package fr.ezzud.ironspellshud.util;

import net.neoforged.fml.loading.LoadingModList;
import net.neoforged.fml.ModList;
import net.neoforged.fml.loading.moddiscovery.ModInfo;

public class PlatformUtil {
    public static boolean isModLoaded(String modId) {
        if (ModList.get() == null) {
            return LoadingModList.get().getMods().stream().map(ModInfo::getModId).anyMatch(modId::equals);
        }
        return ModList.get().isLoaded(modId);
    }
}
