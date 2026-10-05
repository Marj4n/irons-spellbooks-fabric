package org.marj4n.irons_spellbooks.ironlib.statue.block.statue_block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraft.world.phys.shapes.*;
import java.util.Map;

/** Decorative multiblock statue placement and collision adapted from IronLib 2.1.0. */
public abstract class AbstractStatueBlock extends BaseEntityBlock {
    public static final IntegerProperty X_POS = IntegerProperty.create("x_offset", 0, 3);
    public static final IntegerProperty Y_POS = IntegerProperty.create("y_offset", 0, 3);
    public static final IntegerProperty Z_POS = IntegerProperty.create("z_offset", 0, 3);
    public static final IntegerProperty ROTATION = BlockStateProperties.ROTATION_16;
    public static final int MAX = RotationSegment.getMaxSegmentIndex();
    public final int xSize, ySize, zSize;
    private final Map<BlockState, VoxelShape> shapesCache;
    public AbstractStatueBlock(int xSize, int ySize, int zSize) {
        super(BlockBehaviour.Properties.copy(Blocks.STONE).noOcclusion());
        this.xSize = xSize; this.ySize = ySize; this.zSize = zSize;
        registerDefaultState(stateDefinition.any().setValue(ROTATION, 0));
        shapesCache = getShapeForEachState(this::makeShape);
    }
    private VoxelShape makeShape(BlockState state) {
        int x = -16 * state.getValue(X_POS) + 2;
        int y = -16 * state.getValue(Y_POS);
        int z = -16 * state.getValue(Z_POS) + 2;
        return Block.box(x, y, z, x + xSize * 16 - 4, y + ySize * 16, z + zSize * 16 - 4);
    }
    @Override public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos clicked = context.getClickedPos();
        BlockPos.MutableBlockPos origin = clicked.mutable();
        float rotation = Mth.wrapDegrees(context.getRotation());
        Direction face = context.getClickedFace();
        if (face == Direction.DOWN) origin.move(Direction.DOWN, ySize - 1);
        if ((face.getAxis() == Direction.Axis.X) ^ rotation > 0) origin.move(Direction.WEST, xSize - 1);
        if ((face.getAxis() == Direction.Axis.Z) ^ Mth.abs(rotation) > 90) origin.move(Direction.NORTH, zSize - 1);
        for (BlockPos pos : BlockPos.betweenClosed(origin, origin.offset(xSize - 1, ySize - 1, zSize - 1)))
            if (!level.getBlockState(pos).canBeReplaced(context) || !level.getWorldBorder().isWithinBounds(pos)) return null;
        return defaultBlockState().setValue(ROTATION, RotationSegment.convertToSegment(context.getRotation()))
                .setValue(X_POS, clicked.getX()-origin.getX()).setValue(Y_POS, clicked.getY()-origin.getY())
                .setValue(Z_POS, clicked.getZ()-origin.getZ());
    }
    @Override public void setPlacedBy(Level level, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        if (level.isClientSide) return;
        BlockPos origin = pos.offset(-state.getValue(X_POS), -state.getValue(Y_POS), -state.getValue(Z_POS));
        for (int x=0; x<xSize; x++) for (int y=0; y<ySize; y++) for (int z=0; z<zSize; z++) {
            BlockPos fill = origin.offset(x,y,z);
            if (fill.equals(pos)) continue;
            level.setBlock(fill, defaultBlockState().setValue(ROTATION, state.getValue(ROTATION))
                    .setValue(X_POS,x).setValue(Y_POS,y).setValue(Z_POS,z), 3);
            level.blockUpdated(fill, Blocks.AIR);
        }
    }
    @Override public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return shapesCache.get(state);
    }
    @Override public BlockState updateShape(BlockState state, Direction face, BlockState neighbor,
            LevelAccessor level, BlockPos pos, BlockPos neighborPos) {
        BlockPos origin = pos.offset(-state.getValue(X_POS), -state.getValue(Y_POS), -state.getValue(Z_POS));
        for (BlockPos part : BlockPos.betweenClosed(origin, origin.offset(xSize-1,ySize-1,zSize-1))) {
            if (level.getBlockState(part).is(this)) continue;
            level.setBlock(pos, Blocks.AIR.defaultBlockState(), 35);
            level.levelEvent(null, 2001, origin.offset(state.getValue(X_POS)*2,
                    state.getValue(Y_POS)*2, state.getValue(Z_POS)*2), Block.getId(state));
            return Blocks.AIR.defaultBlockState();
        }
        return super.updateShape(state, face, neighbor, level, pos, neighborPos);
    }
    @Override public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(ROTATION, rotation.rotate(state.getValue(ROTATION), MAX+1));
    }
    @Override public BlockState mirror(BlockState state, Mirror mirror) {
        return state.setValue(ROTATION, mirror.mirror(state.getValue(ROTATION), MAX+1));
    }
    @Override protected void createBlockStateDefinition(StateDefinition.Builder<Block,BlockState> builder) {
        super.createBlockStateDefinition(builder); builder.add(ROTATION,X_POS,Y_POS,Z_POS);
    }
    @Override public RenderShape getRenderShape(BlockState state) { return RenderShape.ENTITYBLOCK_ANIMATED; }
}
