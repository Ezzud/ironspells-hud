package fr.ezzud.ironspellshud.handlers;

import fr.ezzud.ironspellshud.util.PlatformUtil;
import net.minecraft.client.Minecraft;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.world.entity.player.Player;

import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA;

public class ManaProvider {
    public double getCurrentMana() {
        // Totally redundant but prevents unreachable statement compile errors
        if (PlatformUtil.isModLoaded( "irons_spellbooks")) {
            return ClientMagicData.getPlayerMana();
        }
        return 0;
    }

    public float getMaxMana() {
        Player player = Minecraft.getInstance().player;
        if (player != null) {
            var maxManaAttribute = player.getAttribute(MAX_MANA);
            if (maxManaAttribute != null) {
                return (float) maxManaAttribute.getValue();
            }
        }
        return 0;
    }
}
