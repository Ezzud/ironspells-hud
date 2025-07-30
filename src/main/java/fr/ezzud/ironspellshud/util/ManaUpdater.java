package fr.ezzud.ironspellshud.util;

import com.mojang.logging.LogUtils;
import fr.ezzud.ironspellshud.registry.AttachmentRegistry;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;

import static fr.ezzud.ironspellshud.Ironspellshud.manaProvider;

public class ManaUpdater {

    public static void updateManaAttributes(ServerPlayer player) {
        if (player != null) {
            int currentMana = (int) manaProvider.getCurrentMana();
            int maxMana = (int) manaProvider.getMaxMana();

            if(player.hasData(AttachmentRegistry.CURRENT_MANA) && player.hasData(AttachmentRegistry.MAX_MANA)) {
                int attachmentCurrentMana = player.getData(AttachmentRegistry.CURRENT_MANA).intValue();
                int attachmentMaxMana = player.getData(AttachmentRegistry.MAX_MANA).intValue();

                if( currentMana != attachmentCurrentMana) {
                    player.setData(AttachmentRegistry.CURRENT_MANA, currentMana);
                }
                if( maxMana != attachmentMaxMana) {
                    player.setData(AttachmentRegistry.MAX_MANA, maxMana);
                }
            } else {
                player.setData(AttachmentRegistry.CURRENT_MANA, currentMana);
                player.setData(AttachmentRegistry.MAX_MANA, maxMana);
                LogUtils.getLogger().info("[SERVER] Setting up server-side mana attributes for player: {}", player.getName().getString());
            }
        }
    }

    public static void updateClientManaAttributes(Player player) {
        if (player != null) {
            int currentMana = (int) manaProvider.getCurrentMana();
            int maxMana = (int) manaProvider.getMaxMana();

            if(player.hasData(AttachmentRegistry.CURRENT_MANA) && player.hasData(AttachmentRegistry.MAX_MANA)) {
                int attachmentCurrentMana = player.getData(AttachmentRegistry.CURRENT_MANA).intValue();
                int attachmentMaxMana = player.getData(AttachmentRegistry.MAX_MANA).intValue();

                if( currentMana != attachmentCurrentMana) {
                    player.setData(AttachmentRegistry.CURRENT_MANA, currentMana);
                }
                if( maxMana != attachmentMaxMana) {
                    player.setData(AttachmentRegistry.MAX_MANA, maxMana);
                }
            } else {
                player.setData(AttachmentRegistry.CURRENT_MANA, currentMana);
                player.setData(AttachmentRegistry.MAX_MANA, maxMana);
                LogUtils.getLogger().info("[CLIENT] Setting up client-side mana attributes for player: {}", player.getName().getString());
            }
        }
    }
}
