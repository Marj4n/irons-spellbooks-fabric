package io.redspace.ironsspellbooks.registries;


import org.marj4n.irons_spellbooks.ironlib.statue.block.statue_block.AbstractStatueBlock;
import org.marj4n.irons_spellbooks.ironlib.statue.block.statue_block.decorative.DecorativeStatueBlockEntity;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.api.backwards_compat.blocks.trial_spawner.TrialSpawnerBlock;
import io.redspace.ironsspellbooks.api.backwards_compat.blocks.trial_spawner.TrialSpawnerBlockEntity;
import io.redspace.ironsspellbooks.api.backwards_compat.blocks.vault.VaultBlock;
import io.redspace.ironsspellbooks.api.backwards_compat.blocks.vault.data.VaultBlockEntity;
import io.redspace.ironsspellbooks.block.ArmorPileBlock;
import io.redspace.ironsspellbooks.block.BloodCauldronBlock;
import io.redspace.ironsspellbooks.block.BoneVaultBlock;
import io.redspace.ironsspellbooks.block.BookStackBlock;
import io.redspace.ironsspellbooks.block.BrazierBlock;
import io.redspace.ironsspellbooks.block.CinderousVaultBlock;
import io.redspace.ironsspellbooks.block.FireflyJar;
import io.redspace.ironsspellbooks.block.VoidstoneBlock;
import io.redspace.ironsspellbooks.block.alchemist_cauldron.AlchemistCauldronBlock;
import io.redspace.ironsspellbooks.block.alchemist_cauldron.AlchemistCauldronTile;
import io.redspace.ironsspellbooks.block.arcane_anvil.ArcaneAnvilBlock;
import io.redspace.ironsspellbooks.block.ice_spider_egg.CookedIceSpiderEggBlock;
import io.redspace.ironsspellbooks.block.ice_spider_egg.IceSpiderEggBlock;
import io.redspace.ironsspellbooks.block.inscription_table.InscriptionTableBlock;
import io.redspace.ironsspellbooks.block.pedestal.PedestalBlock;
import io.redspace.ironsspellbooks.block.pedestal.PedestalTile;
import io.redspace.ironsspellbooks.block.portal_frame.PocketDimensionPortalFrameBlock;
import io.redspace.ironsspellbooks.block.portal_frame.PortalFrameBlock;
import io.redspace.ironsspellbooks.block.portal_frame.PortalFrameBlockEntity;
import io.redspace.ironsspellbooks.block.scroll_forge.ScrollForgeBlock;
import io.redspace.ironsspellbooks.block.scroll_forge.ScrollForgeTile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;

import org.marj4n.irons_spellbooks.registry.DeferredRegistry;
import org.marj4n.irons_spellbooks.registry.RegistryEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;


public class BlockRegistry {
    private static final DeferredRegistry<Block> BLOCKS = DeferredRegistry.create(Registries.BLOCK, IronsSpellbooks.MODID);
    private static final DeferredRegistry<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegistry.create(Registries.BLOCK_ENTITY_TYPE, IronsSpellbooks.MODID);

    public static void register() {
        BLOCKS.register();
        BLOCK_ENTITIES.register();
    }

    public static final RegistryEntry<Block> INSCRIPTION_TABLE_BLOCK = BLOCKS.register("inscription_table", InscriptionTableBlock::new);
    public static final RegistryEntry<Block> SCROLL_FORGE_BLOCK = BLOCKS.register("scroll_forge", ScrollForgeBlock::new);
    public static final RegistryEntry<Block> PEDESTAL_BLOCK = BLOCKS.register("pedestal", PedestalBlock::new);
    public static final RegistryEntry<Block> BLOOD_CAULDRON_BLOCK = BLOCKS.register("blood_cauldron", BloodCauldronBlock::new);
    public static final RegistryEntry<Block> ARCANE_ANVIL_BLOCK = BLOCKS.register("arcane_anvil", ArcaneAnvilBlock::new);
    public static final RegistryEntry<Block> ARMOR_PILE_BLOCK = BLOCKS.register("armor_pile", ArmorPileBlock::new);
    public static final RegistryEntry<Block> ALCHEMIST_CAULDRON = BLOCKS.register("alchemist_cauldron", AlchemistCauldronBlock::new);
    public static final RegistryEntry<Block> FIREFLY_JAR = BLOCKS.register("firefly_jar", FireflyJar::new);
    public static final RegistryEntry<Block> PORTAL_FRAME = BLOCKS.register("portal_frame", () -> new PortalFrameBlock());
    public static final RegistryEntry<Block> BRAZIER_FIRE = BLOCKS.register("brazier", () -> new BrazierBlock(false));
    public static final RegistryEntry<Block> BRAZIER_SOUL = BLOCKS.register("brazier_soul", () -> new BrazierBlock(true));
    public static final RegistryEntry<Block> CINDEROUS_KEYSTONE = BLOCKS.register("cinderous_soul_rune", () -> new Block(BlockBehaviour.Properties.of().lightLevel(state -> 15).pushReaction(PushReaction.BLOCK).sound(SoundType.DEEPSLATE).noLootTable().strength(40, 1200.0F)));
    public static final RegistryEntry<Block> MITHRIL_ORE = BLOCKS.register("mithril_ore", () -> new Block(BlockBehaviour.Properties.of().lightLevel(state -> 9).mapColor(DyeColor.GRAY).requiresCorrectToolForDrops().strength(20.0F, 1200.0F).sound(SoundType.ANCIENT_DEBRIS)));
    public static final RegistryEntry<Block> MITHRIL_ORE_DEEPSLATE = BLOCKS.register("deepslate_mithril_ore", () -> new Block(BlockBehaviour.Properties.of().lightLevel(state -> 9).mapColor(DyeColor.GRAY).requiresCorrectToolForDrops().strength(20.0F, 1200.0F).sound(SoundType.ANCIENT_DEBRIS)));
    public static final RegistryEntry<Block> ICE_SPIDER_EGG = BLOCKS.register("ice_spider_egg", () -> new IceSpiderEggBlock(BlockBehaviour.Properties.of().mapColor(DyeColor.GRAY).strength(3, 1).noOcclusion()));
    public static final RegistryEntry<Block> COOKED_ICE_SPIDER_EGG = BLOCKS.register("cooked_ice_spider_egg",
            () -> new CookedIceSpiderEggBlock(BlockBehaviour.Properties.copy(Blocks.CAKE).sound(SoundType.HONEY_BLOCK)));
    public static final RegistryEntry<Block> BONE_VAULT_BLOCK = BLOCKS.register("bone_vault", () -> new BoneVaultBlock());

    public static final RegistryEntry<Block> CINDEROUS_VAULT_BLOCK = BLOCKS.register("cinderous_vault", () -> new CinderousVaultBlock());

    public static final RegistryEntry<Block> BOOK_STACK = BLOCKS.register("book_stack", BookStackBlock::new);
    public static final RegistryEntry<Block> WISEWOOD_PLANKS = BLOCKS.register("wisewood_planks", () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)));
    public static final RegistryEntry<Block> WISEWOOD_BOOKSHELF = BLOCKS.register("wisewood_bookshelf", () -> new Block(BlockBehaviour.Properties.copy(Blocks.BOOKSHELF)));
    public static final RegistryEntry<Block> GRIMY_TILES = BLOCKS.register("grimy_tiles", () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE)));
    //    public static final RegistryEntry<Block> WISEWOOD_CHISELLED_BOOKSHELF = BLOCKS.register("wisewood_chiseled_bookshelf", () -> new WisewoodChiseledBookshelfBlock(BlockBehaviour.Properties.copy(Blocks.CHISELED_BOOKSHELF)));
    public static final RegistryEntry<Block> NETHER_BRICK_PILLAR = BLOCKS.register("nether_brick_pillar", () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.NETHER_BRICKS)));

    public static final RegistryEntry<Block> VOIDSTONE = BLOCKS.register("voidstone", VoidstoneBlock::new);
    public static final RegistryEntry<Block> POCKET_PORTAL_FRAME = BLOCKS.register("pocket_dimension_portal_frame", PocketDimensionPortalFrameBlock::new);

    public static final RegistryEntry<Block> ARCANE_DEBRIS = BLOCKS.register("arcane_debris", () -> new Block(BlockBehaviour.Properties.of().mapColor(DyeColor.WHITE).requiresCorrectToolForDrops().strength(20.0F, 1200.0F).sound(SoundType.ANCIENT_DEBRIS)));

    public static final RegistryEntry<BlockEntityType<ScrollForgeTile>> SCROLL_FORGE_TILE = BLOCK_ENTITIES.register("scroll_forge", () -> BlockEntityType.Builder.of(ScrollForgeTile::new, SCROLL_FORGE_BLOCK.get()).build(null));
    public static final RegistryEntry<BlockEntityType<PedestalTile>> PEDESTAL_TILE = BLOCK_ENTITIES.register("pedestal", () -> BlockEntityType.Builder.of(PedestalTile::new, PEDESTAL_BLOCK.get()).build(null));
    public static final RegistryEntry<BlockEntityType<AlchemistCauldronTile>> ALCHEMIST_CAULDRON_TILE = BLOCK_ENTITIES.register("alchemist_cauldron", () -> BlockEntityType.Builder.of(AlchemistCauldronTile::new, ALCHEMIST_CAULDRON.get()).build(null));
    public static final RegistryEntry<BlockEntityType<PortalFrameBlockEntity>> PORTAL_FRAME_BLOCK_ENTITY = BLOCK_ENTITIES.register("portal_frame", () -> BlockEntityType.Builder.of(PortalFrameBlockEntity::new, PORTAL_FRAME.get(), POCKET_PORTAL_FRAME.get()).build(null));
//    public static final RegistryEntry<BlockEntityType<WisewoodChiseledBookShelfBlockEntity>> WISEWOOD_CHISELED_BOOKSHELF_ENTITY = BLOCK_ENTITIES.register("wisewood_chiseled_bookshelf", () -> BlockEntityType.Builder.of(WisewoodChiseledBookShelfBlockEntity::new, WISEWOOD_CHISELLED_BOOKSHELF.get()).build(null));

    public static final RegistryEntry<AbstractStatueBlock> TYROS_STATUE_BLOCK = BLOCKS.register("tyros_statue", () -> new AbstractStatueBlock(2, 4, 2) {
        @Override
        public @Nullable BlockEntity newBlockEntity(@NotNull BlockPos pos, @NotNull BlockState state) {
            return new DecorativeStatueBlockEntity(TYROS_STATUE_BLOCK_ENTITY.get(), pos, state);
        }
    });

    public static final RegistryEntry<Block> TRIAL_SPAWNER = BLOCKS.register("trial_spawner", TrialSpawnerBlock::new);
    public static final RegistryEntry<BlockEntityType<TrialSpawnerBlockEntity>> TRIAL_SPAWNER_BLOCK_ENTITY = BLOCK_ENTITIES.register("trial_spawner",
            () -> BlockEntityType.Builder.of(TrialSpawnerBlockEntity::new, TRIAL_SPAWNER.get()).build(null));
    public static final RegistryEntry<Block> VAULT = BLOCKS.register("vault", VaultBlock::new);
    public static final RegistryEntry<BlockEntityType<VaultBlockEntity>> VAULT_BLOCK_ENTITY = BLOCK_ENTITIES.register("vault",
            () -> BlockEntityType.Builder.of(VaultBlockEntity::new, VAULT.get(), BONE_VAULT_BLOCK.get(), CINDEROUS_VAULT_BLOCK.get()).build(null));


    public static final RegistryEntry<BlockEntityType<DecorativeStatueBlockEntity>> TYROS_STATUE_BLOCK_ENTITY = BLOCK_ENTITIES.register("tyros_statue",
            () -> BlockEntityType.Builder.of(DecorativeStatueBlockEntity.from(BlockRegistry.TYROS_STATUE_BLOCK_ENTITY), TYROS_STATUE_BLOCK.get()).build(null));

    public static Collection<RegistryEntry<? extends Block>> blocks() {
        return BLOCKS.getEntries();
    }
}
