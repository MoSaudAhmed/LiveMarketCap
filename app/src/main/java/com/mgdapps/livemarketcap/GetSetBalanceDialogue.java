package com.mgdapps.livemarketcap;

/**
 * Created by HP on 1/7/2018.
 */

public class GetSetBalanceDialogue {

    String name;
    String currentPrice;
    String estimationPrice;
    String availableCoins;

    public String getAvailableCoins() {
        return availableCoins;
    }

    public void setAvailableCoins(String availableCoins) {
        this.availableCoins = availableCoins;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(String currentPrice) {
        this.currentPrice = currentPrice;
    }

    public String getEstimationPrice() {
        return estimationPrice;
    }

    public void setEstimationPrice(String estimationPrice) {
        this.estimationPrice = estimationPrice;
    }
}
