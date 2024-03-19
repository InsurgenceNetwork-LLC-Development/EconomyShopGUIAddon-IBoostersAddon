package com.insurgencedev.economyshopguiaddon.listeners;

import com.google.common.util.concurrent.AtomicDouble;
import me.gypopo.economyshopgui.api.events.PreTransactionEvent;
import me.gypopo.economyshopgui.util.Transaction;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.data.BoosterFindResult;
import org.insurgencedev.insurgenceboosters.data.PermanentBoosterData;

import java.util.Optional;

public final class EconomyShopEventListener implements Listener {

    private final String TYPE = "Sell";
    private final String NAMESPACE = "ECONOMY_SHOPGUI";

    @EventHandler(ignoreCancelled = true)
    public void onSell(PreTransactionEvent event) {
        Transaction.Type type = event.getTransactionType();
        if (!type.getMode().equalsIgnoreCase("sold")) {
            return;
        }

        Player player = event.getPlayer();
        AtomicDouble totalMulti = new AtomicDouble(getPersonalPermMulti(player) + getGlobalPermMulti());

        BoosterFindResult pResult = IBoosterAPI.INSTANCE.getCache(event.getPlayer()).getBoosterDataManager().findActiveBooster(TYPE, NAMESPACE);
        if (pResult instanceof BoosterFindResult.Success boosterResult) {
            totalMulti.getAndAdd(boosterResult.getBoosterData().getMultiplier());
        }

        IBoosterAPI.INSTANCE.getGlobalBoosterManager().findGlobalBooster(TYPE, NAMESPACE, globalBooster -> {
            totalMulti.getAndAdd(globalBooster.getMultiplier());
            return null;
        }, () -> null);

        if (totalMulti.get() > 0) {
            if (type.equals(Transaction.Type.SELL_GUI_SCREEN) || type.equals(Transaction.Type.SELL_ALL_SCREEN) ||
                    type.equals(Transaction.Type.SELL_ALL_COMMAND)) {

                event.getPrices().replaceAll((k, v) -> calculateAmount(v, totalMulti.get()));
                return;
            }

            event.setPrice(calculateAmount(event.getPrice(), totalMulti.get()));
        }
    }

    private double getPersonalPermMulti(Player uuid) {
        Optional<PermanentBoosterData> foundMulti = Optional.ofNullable(IBoosterAPI.INSTANCE.getCache(uuid).getPermanentBoosts().getPermanentBooster(TYPE, NAMESPACE));
        return foundMulti.map(PermanentBoosterData::getMulti).orElse(0d);
    }

    private double getGlobalPermMulti() {
        AtomicDouble multi = new AtomicDouble(0d);

        IBoosterAPI.INSTANCE.getGlobalBoosterManager().findPermanentBooster(TYPE, NAMESPACE, data -> {
            multi.set(data.getMulti());
            return null;
        }, () -> null);

        return multi.get();
    }

    private double calculateAmount(double amount, double multi) {
        return amount * (multi < 1 ? 1 + multi : multi);
    }
}
