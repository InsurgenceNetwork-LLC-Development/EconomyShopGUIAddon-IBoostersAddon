package com.insurgencedev.economyshopguiaddon;

import com.insurgencedev.economyshopguiaddon.listeners.EconomyShopEventListener;
import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;

@IBoostersAddon(name = "EconomyShopGUIAddon", version = "1.0.0", author = "InsurgenceDev", description = "EconomyShopGUI Support")
public class EconomyShopGUIAddon extends InsurgenceBoostersAddon {

    @Override
    public void onAddonReloadablesStart() {
        registerEvent(new EconomyShopEventListener());
    }
}
