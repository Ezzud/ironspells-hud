package fr.ezzud.ironspellshud.registry;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import fr.ezzud.ironspellshud.Ironspellshud;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class AttachmentRegistry {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Ironspellshud.MODID);

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
        LogUtils.getLogger().info("Registered Iron's Spells HUD attachment types");
    }

    public static final Supplier<AttachmentType<Integer>> CURRENT_MANA = ATTACHMENT_TYPES.register(
            "current_mana", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> MAX_MANA = ATTACHMENT_TYPES.register(
            "max_mana", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    static {
        LogUtils.getLogger().info("Registered Iron's Spells HUD attachment types: CURRENT_MANA and MAX_MANA");
    }
}