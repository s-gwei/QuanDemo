package com.sun.quandemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

// 合约类（Contract）
@Data
@JsonIgnoreProperties(ignoreUnknown = true)  // 忽略所有未知字段
public class Contract {
    // 交易对的名称，例如 "BTCUSDT"
    private String symbol;

    // 当前交易对的状态，可能的值：TRADING, BREAK, DELISTED, PRE_TRADING, POST_TRADING
    private String status;

    // 合约的基础资产，例如 "BTC"
    private String baseAsset;

    // 合约的报价资产，例如 "USDT"
    private String quoteAsset;

    // 合约类型，可能的值：PERPETUAL（永续合约），FUTURE（期货合约）
    private String contractType;

    // 合约的交割日期（如果是期货合约），以毫秒为单位，永续合约为null
    private Long deliveryDate;

    // 合约的上架日期（以毫秒为单位）
    private Long onboardDate;

    // 支持的订单类型，例如 ["LIMIT", "MARKET", "STOP_MARKET", "STOP_LIMIT"]
    private List<String> orderTypes;

    // 支持的时间条件，例如 ["GTC", "IOC", "FOK"]
    private List<String> timeInForce;

    // 是否支持冰山单（Iceberg Order）
    private Boolean icebergAllowed;

    // 是否支持 OCO（One Cancels the Other）订单
    private Boolean ocoAllowed;

    // 是否允许市价订单使用报价资产数量进行交易
    private Boolean quoteOrderQtyMarketAllowed;

    // 是否允许设置跟踪止损订单
    private Boolean allowTrailingStop;

    // 是否允许取消并替换订单
    private Boolean cancelReplaceAllowed;

    // 是否允许现货交易
    private Boolean isSpotTradingAllowed;

    // 是否允许使用保证金进行交易
    private Boolean isMarginTradingAllowed;

}
