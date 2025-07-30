package fr.ezzud.ironspellshud.handlers;

import com.mojang.logging.LogUtils;
import com.mojang.serialization.Codec;
import fr.ezzud.ironspellshud.Ironspellshud;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;
import static net.neoforged.neoforgespi.ILaunchContext.LOGGER;

public class AttributeRegistry {
    private static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(NeoForgeRegistries.ATTACHMENT_TYPES, Ironspellshud.MODID);

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
        LogUtils.getLogger().info("Registered Ironspellshud attachment types");
    }


    public static final Supplier<AttachmentType<Integer>> CURRENT_MANA = ATTACHMENT_TYPES.register(
            "current_mana", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );
    public static final Supplier<AttachmentType<Integer>> MAX_MANA = ATTACHMENT_TYPES.register(
            "max_mana", () -> AttachmentType.builder(() -> 0).serialize(Codec.INT).build()
    );

    static {
        LogUtils.getLogger().info("Registered Ironspellshud attachment types: CURRENT_MANA and MAX_MANA");
    }
}