package com.insurgencedev.economyshopguiaddon;

import com.insurgencedev.economyshopguiaddon.listeners.EconomyShopEventListener;
import org.insurgencedev.insurgenceboosters.api.addon.IBoostersAddon;
import org.insurgencedev.insurgenceboosters.api.addon.InsurgenceBoostersAddon;
import org.insurgencedev.insurgenceboosters.libs.fo.Common;

@IBoostersAddon(name = "EconomyShopGUIAddon", version = "1.0.3", author = "InsurgenceDev", description = "EconomyShopGUI Support")
public class EconomyShopGUIAddon extends InsurgenceBoostersAddon {

    @Override
    public void onAddonReloadAblesStart() {
        if (Common.doesPluginExist("EconomyShopGUI")) {
            registerEvent(new EconomyShopEventListener());
        }
    }
}
