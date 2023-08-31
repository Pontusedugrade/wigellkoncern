package org.example.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ExchangeRateService {

    public Map<String, Object> getExchangeRates(){
        String url = "https://openexchangerates.org/api/latest.json?app_id=cb66c6ac2c0b44cfabe2db7b73fbdc09&symbols=,EUR,SEK";
        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(url, Map.class);
    }
}
