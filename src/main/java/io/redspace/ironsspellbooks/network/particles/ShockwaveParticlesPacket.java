package io.redspace.ironsspellbooks.network.particles;

import io.redspace.ironsspellbooks.api.backwards_compat.CustomPacketPayload;
import io.redspace.ironsspellbooks.player.ClientSpellCastHelper;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import org.marj4n.irons_spellbooks.network.FabricPacketContext;
import net.minecraft.core.registries.BuiltInRegistries;

import java.util.Objects;
import java.util.function.Supplier;

public class ShockwaveParticlesPacket implements CustomPacketPayload {
    Vec3 pos;
    float radius;
    String particleName;

    public ShockwaveParticlesPacket(Vec3 pos, float radius, ParticleType particleType) {
        this.pos = pos;
        this.radius = radius;
        this.particleName = Objects.requireNonNull(BuiltInRegistries.PARTICLE_TYPE.getKey(particleType)).toString();
    }

    public ShockwaveParticlesPacket(FriendlyByteBuf buf) {
        this.pos = readVec3(buf);
        this.radius = buf.readFloat();
        this.particleName = buf.readUtf();
    }

    public void toBytes(FriendlyByteBuf buf) {
        writeVec3(pos, buf);
        buf.writeFloat(radius);
        buf.writeUtf(particleName);
    }

    public Vec3 readVec3(FriendlyByteBuf buf) {
        double x = buf.readDouble();
        double y = buf.readDouble();
        double z = buf.readDouble();
        return new Vec3(x, y, z);
    }

    public void writeVec3(Vec3 vec3, FriendlyByteBuf buf) {
        buf.writeDouble(vec3.x);
        buf.writeDouble(vec3.y);
        buf.writeDouble(vec3.z);
    }


    public boolean handle(Supplier<FabricPacketContext> supplier) {
        FabricPacketContext ctx = supplier.get();
        ctx.enqueueWork(() -> {
            try {
                var type = BuiltInRegistries.PARTICLE_TYPE.get(new ResourceLocation(particleName));
                ClientSpellCastHelper.handleClientboundShockwaveParticle(pos, radius, type);
            } catch (Exception ignored) {
            }
        });
        return true;
    }
}
