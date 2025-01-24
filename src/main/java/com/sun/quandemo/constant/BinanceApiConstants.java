package com.sun.quandemo.constant;

/**
 * 币安API地址常量类
 */
public class BinanceApiConstants {
    
    /**
     * 域名常量，现货，u本位，币本位
     */
    public static final String SPOT_DOMAIN = "https://api.binance.com";
    public static final String USD_FUTURE_DOMAIN = "https://fapi.binance.com";
    public static final String COIN_FUTURE_DOMAIN = "https://dapi.binance.com";

    /**
     * 现货API路径
     */
    public static final String SPOT_EXCHANGE_INFO = "/api/v3/exchangeInfo";
    public static final String SPOT_TICKER_24HR = "/api/v3/ticker/24hr";
    public static final String SPOT_KLINES = "/api/v3/klines";

    /**
     * U本位合约API路径
     */
    public static final String USD_EXCHANGE_INFO = "/fapi/v1/exchangeInfo";
    public static final String USD_FUNDING_RATE = "/fapi/v1/fundingRate";
    public static final String USD_PREMIUM_INDEX = "/fapi/v1/premiumIndex";

    /**
     * 币本位合约API路径
     */
    public static final String COIN_EXCHANGE_INFO = "/dapi/v1/exchangeInfo";
    public static final String COIN_FUNDING_RATE = "/dapi/v1/fundingRate";
    public static final String COIN_PREMIUM_INDEX = "/dapi/v1/premiumIndex";

    /**
     * 获取完整的API URL
     * @param domain API域名
     * @param path API路径
     * @return 完整的API URL
     */
    public static String buildUrl(String domain, String path) {
        return domain + path;
    }

    /**
     * 获取完整的API URL，支持带参数
     * @param domain API域名
     * @param path API路径
     * @param params URL参数
     * @return 完整的API URL
     */
    public static String buildUrl(String domain, String path, String params) {
        return domain + path + params;
    }
} 