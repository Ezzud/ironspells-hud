package fr.ezzud.ironspellshud;

import com.mojang.logging.LogUtils;
import fr.ezzud.ironspellshud.event.ClientTick;
import fr.ezzud.ironspellshud.event.ServerTick;
import fr.ezzud.ironspellshud.registry.AttachmentRegistry;
import fr.ezzud.ironspellshud.util.ManaProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import org.slf4j.Logger;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

@Mod(Ironspellshud.MODID)
public class Ironspellshud {
    public static final String MODID = "ironspellshud";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static ManaProvider manaProvider;

    public Ironspellshud(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::clientSetup);
        AttachmentRegistry.register(modEventBus);
    }


    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("[CLIENT] Initialized Client-Side of Iron's Spells HUD");
        }
    }

    public void onInitializeServer() {
        manaProvider = new ManaProvider();
        LOGGER.info("[SERVER] Iron's Spells HUD is starting up and mana provider initialized.");
    }

    public void onInitializeClient() {
        manaProvider = new ManaProvider();
        LOGGER.info("[CLIENT] Iron's Spells HUD is starting up and mana provider initialized.");
    }

    public void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(this::onInitializeClient);
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(ClientTick.class);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        this.onInitializeServer();
        NeoForge.EVENT_BUS.register(this);
        NeoForge.EVENT_BUS.register(ServerTick.class);
    }
}