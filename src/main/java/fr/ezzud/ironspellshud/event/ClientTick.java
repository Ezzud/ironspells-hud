package fr.ezzud.ironspellshud.event;

import fr.ezzud.ironspellshud.Ironspellshud;
import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ClientTickEvent;

import static fr.ezzud.ironspellshud.Ironspellshud.manaProvider;
import static fr.ezzud.ironspellshud.util.ManaUpdater.updateClientManaAttributes;

@EventBusSubscriber(modid = Ironspellshud.MODID)
public class ClientTick {

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        Player player = Minecraft.getInstance().player;
        if (player != null && manaProvider != null) {
            updateClientManaAttributes(player);
        }
    }
}
