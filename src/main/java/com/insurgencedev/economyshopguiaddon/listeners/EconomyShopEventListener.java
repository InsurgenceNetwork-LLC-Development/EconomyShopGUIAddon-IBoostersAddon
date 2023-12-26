package com.insurgencedev.economyshopguiaddon.listeners;

import me.gypopo.economyshopgui.api.events.PreTransactionEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.models.booster.GlobalBoosterManager;
import org.insurgencedev.insurgenceboosters.settings.IBoostersPlayerCache;

public final class EconomyShopEventListener implements Listener {

    @EventHandler
    public void onSell(PreTransactionEvent event) {
        String type = "Sell";
        final String NAMESPACE = "ECONOMY_SHOPGUI";
        double totalMulti = 1;

        IBoostersPlayerCache.BoosterFindResult pResult = IBoosterAPI.getCache(event.getPlayer()).findActiveBooster(type, NAMESPACE);
        if (pResult instanceof IBoostersPlayerCache.BoosterFindResult.Success boosterResult) {
            totalMulti += getMulti(boosterResult.getBooster().getMultiplier());
        }

        GlobalBoosterManager.BoosterFindResult gResult = IBoosterAPI.getGlobalBoosterManager().findBooster(type, NAMESPACE);
        if (gResult instanceof GlobalBoosterManager.BoosterFindResult.Success boosterResult) {
            totalMulti += getMulti(boosterResult.getBooster().getMultiplier());
        }

        event.setPrice(event.getPrice() * totalMulti);
    }

    private double getMulti(double amount) {
        return (amount >= 1) ? amount - 1 : amount;
    }
}
