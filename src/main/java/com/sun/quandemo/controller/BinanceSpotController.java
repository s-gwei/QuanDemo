package com.sun.quandemo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.quandemo.model.ResponseData;
import com.sun.quandemo.model.SpotSymbol;
import com.sun.quandemo.constant.BinanceApiConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/binance/spot")
public class BinanceSpotController {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    //现货api
    private static final String SPOT_BASE_URL = BinanceApiConstants.USD_FUTURE_DOMAIN + BinanceApiConstants.USD_EXCHANGE_INFO;

    @GetMapping("/symbols")
    public ResponseData<List<SpotSymbol>> getAllSpotSymbols() {
        log.info("Fetching all Binance spot trading symbols");
        String url = SPOT_BASE_URL;

        try {
            String response = restTemplate.getForObject(url, String.class);
            Map<String, Object> exchangeInfo = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});
            
            List<SpotSymbol> symbols = objectMapper.convertValue(
                exchangeInfo.get("symbols"),
                new TypeReference<List<SpotSymbol>>() {}
            );

            // 过滤出状态为交易中的交易对
            List<SpotSymbol> tradingSymbols = symbols.stream()
                    .filter(symbol -> "TRADING".equals(symbol.getStatus()))
                    .filter(symbol  -> symbol.getIsSpotTradingAllowed())
                    .collect(Collectors.toList());

            log.info("Successfully fetched {} trading symbols", tradingSymbols.size());
            return ResponseData.success(tradingSymbols);

        } catch (Exception e) {
            log.error("Error fetching spot symbols: ", e);
            return ResponseData.error("Failed to fetch spot symbols: " + e.getMessage());
        }
    }
} 