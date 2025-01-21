package com.sun.quandemo.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 币本位永续合约资金费率数据模型
 */
@Data
public class CoinFundingRate {
    
    /**
     * 交易对名称，例如：BTCUSD_PERP
     */
    private String symbol;

    /**
     * 标记价格，用于计算未实现盈亏
     */
    @JsonProperty("markPrice")
    private String markPrice;

    /**
     * 指数价格，反映现货市场价格
     */
    @JsonProperty("indexPrice")
    private String indexPrice;

    /**
     * 预估结算价格
     */
    @JsonProperty("estimatedSettlePrice")
    private String estimatedSettlePrice;

    /**
     * 最近一次的资金费率
     */
    @JsonProperty("lastFundingRate")
    private String lastFundingRate;

    /**
     * 下次资金费率结算时间（时间戳，毫秒）
     */
    @JsonProperty("nextFundingTime")
    private Long nextFundingTime;

    /**
     * 基础利率
     */
    @JsonProperty("interestRate")
    private String interestRate;

    /**
     * 数据更新时间（时间戳，毫秒）
     */
    private Long time;
} 