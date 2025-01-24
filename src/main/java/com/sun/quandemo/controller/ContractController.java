package com.sun.quandemo.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.quandemo.constant.BinanceApiConstants;
import com.sun.quandemo.model.CoinContract;
import com.sun.quandemo.model.CoinFundingRate;
import com.sun.quandemo.model.ResponseData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 币本位合约
 */

@Slf4j
@RestController
@RequestMapping("/api/coin")
public class ContractController {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private static final String SYMBOL = "BTCUSD_PERP";
    private static final int FUNDING_INTERVALS_PER_DAY = 3; // 每天3次资金费率
    private static final int DAYS_PER_YEAR = 365;

    //币本位url
    private static final String CONTRACT_BASE_URL =  BinanceApiConstants.COIN_FUTURE_DOMAIN + BinanceApiConstants.COIN_EXCHANGE_INFO;

    private static final String CONTRACT_FUND_URL =  BinanceApiConstants.COIN_FUTURE_DOMAIN + BinanceApiConstants.COIN_FUNDING_RATE;



    @RequestMapping("/contract")
    public ResponseData getAllCoinSymbols(){
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
            return ResponseData.success(tradingContracts);
        }catch (Exception e){
            log.error("Error fetching spot symbols: ", e);
            return ResponseData.error("Failed to fetch spot symbols: " + e.getMessage());
        }
    }


    // 格式化时间戳
    public static String formatFundingTime(long timestamp) {
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yy-dd-MM HH:mm:ss");
        java.util.Date date = new java.util.Date(timestamp);
        return sdf.format(date);
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

    @RequestMapping("/fundingRate")
    public ResponseData getfundingRate(String symbol){
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

            Map<String, Object> resultData = new HashMap<>();
            resultData.put("data", sortedContracts);
            return ResponseData.success(resultData);
        }catch (Exception e){
            log.error("Error fetching spot symbols: ", e);
            return ResponseData.error("Failed to fetch spot symbols: " + e.getMessage());
        }
    }


    @RequestMapping("/fundingRate/history")
    public ResponseData getfundingRateHis(){
        String url = CONTRACT_FUND_URL + "?symbol=BTCUSD_PERP&limit = 1000";
        try {
            String response = restTemplate.getForObject(url, String.class);
            List<Map<String, Object>> fundingRates = objectMapper.readValue(response,
                    new TypeReference<List<Map<String, Object>>>() {});

            // 计算平均资金费率
            double totalRate = 0.0;
            for (Map<String, Object> rate : fundingRates) {
                totalRate += Double.parseDouble(rate.get("fundingRate").toString());
            }
            double averageFundingRate = totalRate / fundingRates.size();
            double annualRate = averageFundingRate * FUNDING_INTERVALS_PER_DAY * DAYS_PER_YEAR * 100;

            BigDecimal formattedRate = new BigDecimal(annualRate).setScale(3, RoundingMode.HALF_UP);  // 保留3位小数，四舍五入
            BigDecimal formattedAverageFundingRate = new BigDecimal(averageFundingRate * 100).setScale(5, RoundingMode.HALF_UP);  // 保留5位小数，四舍五入

            Map<String, Object> resultData = new HashMap<>();
            resultData.put("1000条平均资金费率", formattedAverageFundingRate);
            resultData.put("年利率", formattedRate);
            log.info("计算1000条资金费率，年收益为{}%", String.format("%.2f", annualRate));
            return ResponseData.success(resultData);
        }catch (Exception e){
            log.error("Error fetching spot symbols: ", e);
            return ResponseData.error("Failed to fetch spot symbols: " + e.getMessage());
        }
    }
}
