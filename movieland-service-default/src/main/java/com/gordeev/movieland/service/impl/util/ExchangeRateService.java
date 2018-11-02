package com.gordeev.movieland.service.impl.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gordeev.movieland.vo.Currency;
import com.gordeev.movieland.vo.ExchangeRate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class ExchangeRateService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final static ObjectMapper mapper = new ObjectMapper();
    private Map<Currency, Double> exchangeRatesMap;

    @Value("${exchangeRate.url}")
    private String urlForExchangeRate;

    @PostConstruct
    @Scheduled(fixedRateString = "${exchangeRate.maxLifeTime}", initialDelayString = "${exchangeRate.maxLifeTime}")
    private void getExchangeRateFromBank() {

        try {
            logger.info("Start to get exchange rate from Bank");

            exchangeRatesMap = new HashMap<>();
            URL urlObject = new URL(urlForExchangeRate);
            HttpURLConnection httpURLConnection = (HttpURLConnection) urlObject.openConnection();
            httpURLConnection.setRequestMethod("GET");

            BufferedReader inputStream = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

            ExchangeRate[] exchangeRates = mapper.readValue(inputStream, ExchangeRate[].class);

            for (ExchangeRate rate : exchangeRates) {
                if (Currency.UAH.getName().equals(rate.getBaseCurrency())) {
                    if (Currency.USD.getName().equals(rate.getCcy())){
                        exchangeRatesMap.put(Currency.USD, Double.parseDouble(rate.getSale()));
                    }
                    if (Currency.EUR.getName().equals(rate.getCcy())){
                        exchangeRatesMap.put(Currency.EUR, Double.parseDouble(rate.getSale()));
                    }
                    exchangeRatesMap.put(Currency.UAH, 1.0);
                }
            }

            logger.info("Exchange rate from Bank received");
        } catch (IOException e) {
            logger.error("Cannot get exchange rate from Bank", e);
        }
    }

    public Map<Currency, Double> getExchangeRatesMap() {
        //TODO: need full copy
        return new HashMap<>(exchangeRatesMap);
    }
}
