package com.gordeev.movieland.service.impl.util

import com.gordeev.movieland.vo.Currency

class ExchangeRateServiceTest extends GroovyTestCase {
    void testGetExchangeRatesMap() {
        ExchangeRateService exchangeRateService = new ExchangeRateService()
        exchangeRateService.urlForExchangeRate = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11"
        exchangeRateService.getExchangeRateFromBank()
        Map<Currency, Double> exchangeRatesMap = exchangeRateService.getExchangeRatesMap()

        assertFalse(exchangeRatesMap.isEmpty())
    }
}
