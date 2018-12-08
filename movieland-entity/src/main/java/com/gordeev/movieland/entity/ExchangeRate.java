package com.gordeev.movieland.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExchangeRate {
    @JsonProperty("ccy")
    private String currency;
    @JsonProperty("base_ccy")
    private String baseCurrency;
    private String buy;
    private String sale;

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getBaseCurrency() {
        return baseCurrency;
    }

    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    public String getBuy() {
        return buy;
    }

    public void setBuy(String buy) {
        this.buy = buy;
    }

    public String getSale() {
        return sale;
    }

    public void setSale(String sale) {
        this.sale = sale;
    }
}
