package com.insurgencedev.economyshopguiaddon.listeners;

import me.gypopo.economyshopgui.api.events.PreTransactionEvent;
import me.gypopo.economyshopgui.util.Transaction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.insurgencedev.insurgenceboosters.api.IBoosterAPI;
import org.insurgencedev.insurgenceboosters.data.BoosterFindResult;

public final class EconomyShopEventListener implements Listener {

    @EventHandler(ignoreCancelled = true)
    public void onSell(PreTransactionEvent event) {
        Transaction.Type type = event.getTransactionType();
        if (!type.getMode().equalsIgnoreCase("sold")) {
            return;
        }

        final String TYPE = "Sell";
        final String NAMESPACE = "ECONOMY_SHOPGUI";
        final double[] totalMulti = {0};

        BoosterFindResult pResult = IBoosterAPI.INSTANCE.getCache(event.getPlayer()).getBoosterDataManager().findActiveBooster(TYPE, NAMESPACE);
        if (pResult instanceof BoosterFindResult.Success boosterResult) {
            totalMulti[0] += boosterResult.getBoosterData().getMultiplier();
        }

        IBoosterAPI.INSTANCE.getGlobalBoosterManager().findGlobalBooster(TYPE, NAMESPACE, globalBooster -> {
            totalMulti[0] += globalBooster.getMultiplier();
            return null;
        }, () -> null);

        if (totalMulti[0] > 0) {
            if (type.equals(Transaction.Type.SELL_GUI_SCREEN) || type.equals(Transaction.Type.SELL_ALL_SCREEN) ||
                    type.equals(Transaction.Type.SELL_ALL_COMMAND)) {

                event.getPrices().replaceAll((k, v) -> calculateAmount(v, totalMulti[0]));
                return;
            }

            event.setPrice(calculateAmount(event.getPrice(), totalMulti[0]));
        }
    }

    private double calculateAmount(double amount, double multi) {
        return amount * (multi < 1 ? 1 + multi : multi);
    }
}
