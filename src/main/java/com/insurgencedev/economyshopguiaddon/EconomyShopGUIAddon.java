package com.insurgencedev.economyshopguiaddon;

import com.insurgencedev.economyshopguiaddon.listeners.EconomyShopEventListener;
import org.bukkit.Bukkit;
import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;

@IBoostersAddon(name = "EconomyShopGUIAddon", version = "1.0.1", author = "InsurgenceDev", description = "EconomyShopGUI Support")
public class EconomyShopGUIAddon extends InsurgenceBoostersAddon {

    @Override
    public void onAddonReloadablesStart() {
        if (Bukkit.getPluginManager().isPluginEnabled("EconomyShopGUI")) {
            registerEvent(new EconomyShopEventListener());
        }
    }
}
