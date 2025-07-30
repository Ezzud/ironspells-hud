package fr.ezzud.ironspellshud.event;

import com.mojang.logging.LogUtils;
import fr.ezzud.ironspellshud.Ironspellshud;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.List;

import static fr.ezzud.ironspellshud.Ironspellshud.manaProvider;
import static fr.ezzud.ironspellshud.util.ManaUpdater.updateManaAttributes;

@EventBusSubscriber(modid = Ironspellshud.MODID)
public class ServerTick {

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        if (manaProvider != null) {
            MinecraftServer server = event.getServer();
            List<ServerPlayer> players = server.getPlayerList().getPlayers();
            if (players.isEmpty()) {
                return;
            }
            for (ServerPlayer serverPlayer : players) {
                updateManaAttributes(serverPlayer);
            }
        }
    }
}
