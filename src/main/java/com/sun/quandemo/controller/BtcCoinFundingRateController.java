package com.sun.quandemo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.sun.quandemo.model.CoinFundingRate;
import com.sun.quandemo.model.ResponseData;

@Slf4j
@RestController
@RequestMapping("/api/btc/coin")
public class BtcCoinFundingRateController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private static final int LIMIT = 1000;
    private static final String COIN_BASE_URL = "https://dapi.binance.com/dapi/v1";
    private static final String SYMBOL = "BTCUSD_PERP";
    private static final int FUNDING_INTERVALS_PER_DAY = 3; // 每天3次资金费率
    private static final int DAYS_PER_YEAR = 365;
    

    @GetMapping("/annual-rate")
    public ResponseData<Map<String, Double>> calculateAnnualRate() {
        log.info("Calculating annual funding rate for BTC coin-margined perpetual");
        String url = String.format("%s/fundingRate?symbol=%s&limit=%d", COIN_BASE_URL, SYMBOL, LIMIT);

        try {
            String response = restTemplate.getForObject(url, String.class);
            List<Map<String, Object>> fundingRates = objectMapper.readValue(response, 
                new TypeReference<List<Map<String, Object>>>() {});
            
            if (fundingRates == null || fundingRates.isEmpty()) {
                return ResponseData.error("No funding rate data available");
            }

            // 计算平均资金费率
            double totalRate = 0.0;
            for (Map<String, Object> rate : fundingRates) {
                totalRate += Double.parseDouble(rate.get("fundingRate").toString());
            }
            double averageFundingRate = totalRate / fundingRates.size();
            double annualRate = averageFundingRate * FUNDING_INTERVALS_PER_DAY * DAYS_PER_YEAR * 100;

            Map<String, Double> resultData = new HashMap<>();
            resultData.put("averageFundingRate", averageFundingRate);
            resultData.put("annualRate", annualRate);

            log.info("Successfully calculated annual funding rate: {}%", String.format("%.2f", annualRate));
            return ResponseData.success(resultData);

        } catch (Exception e) {
            log.error("Error calculating annual funding rate: ", e);
            return ResponseData.error(e.getMessage());
        }
    }

    @GetMapping("/current-rate")
    public ResponseData getCurrentFundingRate() {
        log.info("Getting current funding rate for BTC coin-margined perpetual");
        String url = String.format("%s/premiumIndex?symbol=%s", COIN_BASE_URL, SYMBOL);

        try {
            String response = restTemplate.getForObject(url, String.class);
            List<CoinFundingRate> fundingRate = objectMapper.readValue(response, ArrayList.class);
            
            if (fundingRate == null) {
                return ResponseData.error("No current funding rate data available");
            }

            return ResponseData.success(fundingRate);

        } catch (Exception e) {
            log.error("Error fetching current funding rate: ", e);
            return ResponseData.error(e.getMessage());
        }
    }

    @GetMapping("/max-rate")
    public ResponseData getMaxFundingRate() {
        log.info("Getting current funding rate for BTC coin-margined perpetual");
        String url = String.format("%s/fundingRate?symbol=%s&limit=%d", COIN_BASE_URL, SYMBOL, LIMIT);

        try {
            String response = restTemplate.getForObject(url, String.class);
            List<CoinFundingRate> fundingRate = objectMapper.readValue(response, ArrayList.class);

            if (fundingRate == null) {
                return ResponseData.error("No current funding rate data available");
            }

            return ResponseData.success(fundingRate);

        } catch (Exception e) {
            log.error("Error fetching current funding rate: ", e);
            return ResponseData.error(e.getMessage());
        }
    }

    /**
     * 将时间戳转换为可读的日期时间格式
     */
    private String formatTimestamp(Long timestamp) {
        if (timestamp == null) return "N/A";
        return new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .format(new java.util.Date(timestamp));
    }
} 