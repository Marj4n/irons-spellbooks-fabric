package org.marj4n.irons_spellbooks.ironlib;

import com.google.gson.*;
import io.redspace.ironsspellbooks.IronsSpellbooks;
import io.redspace.ironsspellbooks.item.ChronicleItem;
import io.redspace.ironsspellbooks.registries.ItemRegistry;
import io.redspace.ironsspellbooks.setup.PacketDistributor;
import net.minecraft.nbt.*;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.saveddata.SavedData;
import java.net.URI;
import java.net.http.*;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.concurrent.*;

/** Chronicle service and compatible world cache, replacing the Forge-only IronLib transport. */
public final class ChronicleData {
    private static volatile List<ChronicleEntry> entries = List.of();
    private static ScheduledExecutorService worker;
    private static long lastUpdated;
    public static List<ChronicleEntry> entries() { return entries; }
    public static void update(List<ChronicleEntry> values) {
        entries = List.copyOf(values);
        ((ChronicleItem)ItemRegistry.THE_CHRONICLE.get()).clearCache();
    }
    public static void start(MinecraftServer server) {
        stop();
        var storage = server.overworld().getDataStorage();
        Cache legacy = storage.get(Cache::load,"irons_patreon_lib_data");
        Cache cache = storage.computeIfAbsent(Cache::load,() -> legacy != null ? legacy : new Cache(),"irons_lib_data");
        update(cache.values);
        worker = Executors.newSingleThreadScheduledExecutor(task -> {
            var thread = new Thread(task,"ISS Chronicle"); thread.setDaemon(true); return thread;
        });
        var currentWorker = worker;
        long delay = cache.timestamp > 0 && System.currentTimeMillis()-cache.timestamp < 300000 ? 300 : 0;
        var client = HttpClient.newBuilder().connectTimeout(Duration.ofSeconds(25)).build();
        worker.scheduleWithFixedDelay(() -> {
            try {
                String url = "https://code.redspace.io/cache/game-data?lastUpdated="+lastUpdated
                        +"&id="+cache.id+"&playerCount="+server.getPlayerCount()
                        +"&isDedicated="+server.isDedicatedServer()+"&version=2.1.0";
                var response = client.send(HttpRequest.newBuilder(URI.create(url)).timeout(Duration.ofSeconds(25)).GET().build(),HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() != 200) throw new IllegalStateException("HTTP "+response.statusCode());
                var json = JsonParser.parseString(response.body()).getAsJsonObject();
                if (!json.has("data") || !json.has("lastUpdated")) throw new IllegalStateException("Missing Chronicle data");
                List<ChronicleEntry> result = new ArrayList<>();
                for (var element : json.getAsJsonArray("data")) {
                    try {
                        var entry=element.getAsJsonObject(); int category=entry.get("bookCategory").getAsInt();
                        if(category>=0) result.add(new ChronicleEntry(entry.get("displayName").getAsString(),entry.get("pledgeTier").getAsInt(),category));
                    } catch (RuntimeException invalidEntry) { /* Match upstream: skip malformed individual entries. */ }
                }
                lastUpdated=json.get("lastUpdated").getAsLong();
                var immutable=List.copyOf(result);
                server.execute(() -> {
                    if(worker != currentWorker || currentWorker.isShutdown()) return;
                    cache.values=immutable; cache.timestamp=System.currentTimeMillis(); cache.setDirty(); update(immutable);
                    server.getPlayerList().getPlayers().forEach(player -> PacketDistributor.sendToPlayer(player,new SyncChroniclePacket(immutable)));
                });
            } catch (InterruptedException interrupted) { Thread.currentThread().interrupt(); }
            catch (Exception failure) { IronsSpellbooks.LOGGER.warn("Chronicle service unavailable; retaining cached entries: {}",failure.getMessage()); }
        },delay,300,TimeUnit.SECONDS);
    }
    public static void stop() {
        if(worker != null) worker.shutdownNow(); worker=null; lastUpdated=0;
        entries=List.of();
    }
    private static byte[] encode(String name) {
        byte[] bytes=name.getBytes(StandardCharsets.UTF_8);
        for(int i=0;i<bytes.length;i++) bytes[i]=(byte)~bytes[i]; return bytes;
    }
    private static String decode(byte[] source) {
        byte[] bytes=source.clone(); for(int i=0;i<bytes.length;i++) bytes[i]=(byte)~bytes[i];
        return new String(bytes,StandardCharsets.UTF_8);
    }
    private static final class Cache extends SavedData {
        UUID id=UUID.randomUUID(); long timestamp; List<ChronicleEntry> values=List.of();
        CompoundTag original=new CompoundTag();
        static Cache load(CompoundTag tag) {
            Cache cache=new Cache(); cache.original=tag.copy();
            if(tag.hasUUID("ServerId")) cache.id=tag.getUUID("ServerId");
            cache.timestamp=tag.getLong("PatreonCacheTime");
            List<ChronicleEntry> result=new ArrayList<>(); var list=tag.getList("PatreonChronicle",Tag.TAG_COMPOUND);
            for(int i=0;i<list.size();i++) {
                var entry=list.getCompound(i); short data=entry.getShort("data");
                result.add(new ChronicleEntry(decode(entry.getByteArray("profile")),data&255,(data>>>8)&255));
            }
            cache.values=List.copyOf(result); return cache;
        }
        @Override public CompoundTag save(CompoundTag tag) {
            tag.merge(original); tag.putUUID("ServerId",id); tag.putLong("PatreonCacheTime",timestamp);
            var list=new ListTag();
            for(var entry:values) {
                var value=new CompoundTag(); value.putByteArray("profile",encode(entry.displayName()));
                value.putShort("data",(short)((entry.bookCategory()&255)<<8|(entry.pledge()&255))); list.add(value);
            }
            tag.put("PatreonChronicle",list); return tag;
        }
    }
}
