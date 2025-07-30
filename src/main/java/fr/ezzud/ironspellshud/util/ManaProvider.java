package fr.ezzud.ironspellshud.util;

import net.minecraft.client.Minecraft;
import io.redspace.ironsspellbooks.player.ClientMagicData;
import net.minecraft.world.entity.player.Player;

import static io.redspace.ironsspellbooks.api.registry.AttributeRegistry.MAX_MANA;

public class ManaProvider {
    public double getCurrentMana() {
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
