package fr.ezzud.ironspellshud;

import com.mojang.authlib.minecraft.client.MinecraftClient;
import com.mojang.logging.LogUtils;
import io.redspace.ironsspellbooks.api.events.ChangeManaEvent;
import io.redspace.ironsspellbooks.api.events.SpellOnCastEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.status.ServerStatus;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.dedicated.DedicatedServer;
import net.minecraft.server.players.PlayerList;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent;
import net.neoforged.neoforge.client.event.ClientTickEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;
import org.slf4j.Logger;
import fr.ezzud.ironspellshud.handlers.*;
import net.minecraft.server.level.ServerPlayer;

import java.util.List;

import static net.neoforged.neoforgespi.ILaunchContext.LOGGER;

@Mod(Ironspellshud.MODID)
public class Ironspellshud {
    public static final String MODID = "ironspellshud";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static ManaProvider manaProvider;

    public Ironspellshud(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::clientSetup);
        AttributeRegistry.register(modEventBus);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        manaProvider = new ManaProvider();
        LOGGER.info("[SERVER] Ironspellshud is starting up and mana provider initialized.");
    }

    @SubscribeEvent
    public void onServerTick(ServerTickEvent.Post event) {
        if (manaProvider != null) {
            MinecraftServer server = event.getServer();
            if (server == null) {
                LOGGER.warn("Server is null during ServerTickEvent, cannot update mana attributes.");
                return;
            }
            List<ServerPlayer> players = server.getPlayerList().getPlayers();
            if (players.isEmpty()) {
                return;
            }
            for (ServerPlayer serverPlayer : players) {
                updateManaAttributes(serverPlayer);
            }
        }
    }


    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("[CLIENT] Initialized Client-Side of IronSpellsHUD");
        }

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Player player = Minecraft.getInstance().player;
            if (player != null && manaProvider != null) {
                updateClientManaAttributes(player);
            }
        }
    }

    @EventBusSubscriber(modid = MODID, value = Dist.DEDICATED_SERVER)
    public static class ServerModEvents {
        @SubscribeEvent
        public static void onCommonSetup(FMLDedicatedServerSetupEvent event) {
            LOGGER.info("[SERVER] Initialized Server-Side of IronSpellsHUD");
        }
    }

    public static void updateManaAttributes(ServerPlayer player) {
        if (player != null) {
            int currentMana = (int) manaProvider.getCurrentMana();
            int maxMana = (int) manaProvider.getMaxMana();

            if(player.hasData(AttributeRegistry.CURRENT_MANA) && player.hasData(AttributeRegistry.MAX_MANA)) {
                int attachmentCurrentMana = player.getData(AttributeRegistry.CURRENT_MANA).intValue();
                int attachmentMaxMana = player.getData(AttributeRegistry.MAX_MANA).intValue();

                if( currentMana != attachmentCurrentMana) {
                    player.setData(AttributeRegistry.CURRENT_MANA, currentMana);
                }
                if( maxMana != attachmentMaxMana) {
                    player.setData(AttributeRegistry.MAX_MANA, maxMana);
                }
            } else {
                player.setData(AttributeRegistry.CURRENT_MANA, currentMana);
                player.setData(AttributeRegistry.MAX_MANA, maxMana);
                LOGGER.info("[SERVER] Setting up server-side mana attributes for player: {}", player.getName().getString());
            }
            return;
        }
    }

    public static void updateClientManaAttributes(Player player) {
        if (player != null) {
            int currentMana = (int) manaProvider.getCurrentMana();
            int maxMana = (int) manaProvider.getMaxMana();

            if(player.hasData(AttributeRegistry.CURRENT_MANA) && player.hasData(AttributeRegistry.MAX_MANA)) {
                int attachmentCurrentMana = player.getData(AttributeRegistry.CURRENT_MANA).intValue();
                int attachmentMaxMana = player.getData(AttributeRegistry.MAX_MANA).intValue();

                if( currentMana != attachmentCurrentMana) {
                    player.setData(AttributeRegistry.CURRENT_MANA, currentMana);
                }
                if( maxMana != attachmentMaxMana) {
                    player.setData(AttributeRegistry.MAX_MANA, maxMana);
                }
            } else {
                player.setData(AttributeRegistry.CURRENT_MANA, currentMana);
                player.setData(AttributeRegistry.MAX_MANA, maxMana);
                LOGGER.info("[CLIENT] Setting up client-side mana attributes for player: {}", player.getName().getString());
            }
            return;
        }
    }

    public void onInitializeClient() {
        manaProvider = new ManaProvider();
        LOGGER.info("Ironspellshud Client Initialization Complete");
    }

    public void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(this::onInitializeClient);
        NeoForge.EVENT_BUS.register(this);
    }
}