package fr.ezzud.ironspellshud;

import com.mojang.logging.LogUtils;
import fr.ezzud.ironspellshud.event.ClientTick;
import fr.ezzud.ironspellshud.registry.AttachmentRegistry;
import fr.ezzud.ironspellshud.util.ManaProvider;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import org.apache.maven.artifact.versioning.ComparableVersion;
import org.slf4j.Logger;

@Mod(Ironspellshud.MODID)
public class Ironspellshud {
    public static final String MODID = "ironspellshud";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static ManaProvider manaProvider;

    public Ironspellshud(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::clientSetup);
        AttachmentRegistry.register(modEventBus);
        ComparableVersion version = new ComparableVersion(modContainer.getModInfo().getVersion().toString());
    }

    @EventBusSubscriber(modid = MODID, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("[CLIENT] Initialized Client-Side of Iron's Spells HUD");
        }
    }

    public void onInitializeClient() {
        manaProvider = new ManaProvider();
        LOGGER.info("[CLIENT] Iron's Spells HUD is starting up and mana provider initialized.");
    }

    public void clientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(this::onInitializeClient);
        NeoForge.EVENT_BUS.register(ClientTick.class);
    }
}