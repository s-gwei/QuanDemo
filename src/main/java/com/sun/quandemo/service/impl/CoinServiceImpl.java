package com.sun.quandemo.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.quandemo.constant.BinanceApiConstants;
import com.sun.quandemo.model.CoinContract;
import com.sun.quandemo.model.CoinFundingRate;
import com.sun.quandemo.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CoinServiceImpl {

    //币本位url
    private static final String CONTRACT_BASE_URL =  BinanceApiConstants.COIN_FUTURE_DOMAIN + BinanceApiConstants.COIN_EXCHANGE_INFO;

    private static final String CONTRACT_FUND_URL =  BinanceApiConstants.COIN_FUTURE_DOMAIN + BinanceApiConstants.COIN_FUNDING_RATE;


    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public List<CoinContract> getCoinContract() {
        log.info("Fetching all Binance spot trading symbols");
        // Binance API 端点：获取交易所信息（期货市场）
        String url = CONTRACT_BASE_URL;
        try {
            String response = restTemplate.getForObject(url, String.class);
            Map<String, Object> exchangeInfo = objectMapper.readValue(response, new TypeReference<Map<String, Object>>() {});

            //将交易数据转list
            List<CoinContract> contracts = objectMapper.convertValue(
                    exchangeInfo.get("symbols"),
                    new TypeReference<List<CoinContract>>() {}
            );

            // 	contractType = "PERPETUAL"：这是永续合约。筛选币本位合约：quoteAsset 不是 USDT，并且 baseAsset 是加密货币

            List<CoinContract> tradingContracts = contracts.stream()
                    .filter(symbol -> "TRADING".equals(symbol.getContractStatus()))
                    .filter(symbol   -> "PERPETUAL".equals(symbol.getContractType()))
                    .filter(symbol   -> isCryptoAsset(symbol.getBaseAsset()))
                    .filter(symbol   -> !"USDT".equals(symbol.getQuoteAsset()))
                    .filter(symbol   -> !"USDC".equals(symbol.getQuoteAsset()))
                    .collect(Collectors.toList());
            log.info("Successfully fetched {} trading symbols", tradingContracts.size());
            return tradingContracts;
        }catch (Exception e){
            log.error("Error fetching spot symbols: ", e);
            return null;
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

    public CoinFundingRate getCoinFundingRate(String symbol) {
        if(symbol == null){
            symbol = "BTCUSD_PERP";
        }
        String url = CONTRACT_FUND_URL + "?symbol=" + symbol;
        try {
            String response = restTemplate.getForObject(url, String.class);

            // 解析 JSON 字符串为 List<CoinFundingRate> 对象
            List<CoinFundingRate> contracts = objectMapper.readValue(response, new TypeReference<List<CoinFundingRate>>() {});

            // 按照 fundingTime 字段降序排列
            List<CoinFundingRate> sortedContracts = contracts.stream()
                    .sorted((c1, c2) -> Long.compare(c2.getFundingTime(), c1.getFundingTime())) // fundingTime 降序排列
                    .collect(Collectors.toList());

            return sortedContracts.get(0);
        }catch (Exception e){
            log.error("Error fetching spot symbols: ", e);
            return null;
        }
    }
}
