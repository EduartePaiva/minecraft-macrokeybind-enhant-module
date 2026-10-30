package dev.enchantaddon;

import net.eq2online.macros.scripting.api.APIVersion;
import net.eq2online.macros.scripting.api.IMacrosAPIModule;

@APIVersion(18)
public class EnchantAddon implements IMacrosAPIModule {

    @Override
    public void onInit() {
        System.out.println("[EnchantAddon] Module initialized successfully!");
    }
}