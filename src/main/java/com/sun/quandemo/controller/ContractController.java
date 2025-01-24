package com.sun.quandemo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.quandemo.model.Contract;
import com.sun.quandemo.model.ResponseData;
import com.sun.quandemo.model.SpotSymbol;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.sun.quandemo.constant.BinanceApiConstants.COIN_EXCHANGE_INFO;
import static com.sun.quandemo.constant.BinanceApiConstants.COIN_FUTURE_DOMAIN;

@Slf4j
@RestController
@RequestMapping("/api/contract")
public class ContractController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();
    //现货api
    private static final String SPOT_BASE_URL = "https://api.binance.com/api/v3/exchangeInfo";
    private static final String CONTRACT_BASE_URL =  COIN_FUTURE_DOMAIN + COIN_EXCHANGE_INFO;
//    private static final String CONTRACT_BASE_URL_1  =  "https://fapi.binance.com/dapi/v1/exchangeInfo";


    @RequestMapping("/coin")
    public ResponseData getAllCoinSymbols(){
        log.info("Fetching all Binance spot trading symbols");
        // Binance API 端点：获取交易所信息（期货市场）
        String url = CONTRACT_BASE_URL;
        try {
            String response = restTemplate.getForObject(url, String.class);
            Map<String, Object> exchangeInfo = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});

            //将交易数据转list
            List<Contract> contracts = objectMapper.convertValue(
                    exchangeInfo.get("symbols"),
                    new TypeReference<List<Contract>>() {}
            );

            // 	contractType = "PERPETUAL"：这是永续合约。筛选币本位合约：quoteAsset 不是 USDT，并且 baseAsset 是加密货币

            List<Contract> tradingContracts = contracts.stream()
//                    .filter(symbol -> "TRADING".equals(symbol.getStatus()))
//                    .filter(symbol   -> "PERPETUAL".equals(symbol.getContractType()))
//                    .filter(symbol   -> isCryptoAsset(symbol.getBaseAsset()))
//                    .filter(symbol   -> !"USDT".equals(symbol.getQuoteAsset()))
//                    .filter(symbol   -> !"USDC".equals(symbol.getQuoteAsset()))
                    .collect(Collectors.toList());
            log.info("Successfully fetched {} trading symbols", tradingContracts.size());
            return ResponseData.success(tradingContracts);
        }catch (Exception e){
            log.error("Error fetching spot symbols: ", e);
            return ResponseData.error("Failed to fetch spot symbols: " + e.getMessage());
        }
    }

    // 判断基础资产是否是加密货币
    private boolean isCryptoAsset(String asset) {
        // 基础资产常见的加密货币（补充更多常见币种）
        return "BTC".equals(asset) || "ETH".equals(asset) || "XRP".equals(asset) || "LTC".equals(asset) ||
                "BCH".equals(asset) || "EOS".equals(asset) || "ADA".equals(asset) || "TRX".equals(asset) ||
                "DOT".equals(asset) || "LINK".equals(asset) || "SOL".equals(asset) || "AVAX".equals(asset) ||
                "MATIC".equals(asset) || "DOGE".equals(asset) || "SHIB".equals(asset) || "FIL".equals(asset) ||
                "VET".equals(asset) || "MKR".equals(asset) || "AAVE".equals(asset) || "UNI".equals(asset);
    }

    @RequestMapping("/usdt")
    public ResponseData getAllUSDTSymbols(){
        log.info("Fetching all Binance spot trading symbols");
        // Binance API 端点：获取交易所信息（期货市场）
        String url = CONTRACT_BASE_URL;
        try {
            String response = restTemplate.getForObject(url, String.class);
            Map<String, Object> exchangeInfo = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});

            //将交易数据转list
            List<Contract> contracts = objectMapper.convertValue(
                    exchangeInfo.get("symbols"),
                    new TypeReference<List<Contract>>() {}
            );

            //	contractType = "PERPETUAL"：表示是永续合约。  // 筛选 U本位合约：quoteAsset 是 USDT，并且 baseAsset 是加密货币
            List<Contract> tradingContracts = contracts.stream()
                    .filter(symbol -> "TRADING".equals(symbol.getStatus()))
                    .filter(symbol   -> "PERPETUAL".equals(symbol.getContractType()))
//                    .filter(symbol   -> isCryptoAsset(symbol.getBaseAsset()))
                    .filter(symbol   -> "USDT".equals(symbol.getQuoteAsset()))
                    .collect(Collectors.toList());
            log.info("Successfully fetched {} trading symbols", tradingContracts.size());
            return ResponseData.success(tradingContracts);
        }catch (Exception e){
            log.error("Error fetching spot symbols: ", e);
            return ResponseData.error("Failed to fetch spot symbols: " + e.getMessage());
        }
    }
}
