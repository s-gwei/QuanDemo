package com.sun.quandemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;
import java.util.logging.Filter;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)  // 忽略未知字段
public class CoinContract {
    private String symbol; // 合约名称，如 BTCUSD_PERP
    private String pair; // 交易对名称，如 BTCUSD
    private String contractType; // 合约类型，如 PERPETUAL
    private Long deliveryDate; // 交割日期
    private Long onboardDate; // 合约上架时间
    private String contractStatus; // 合约状态，如 TRADING
    private Integer contractSize; // 合约大小
    private String marginAsset; // 保证金资产
    private String maintMarginPercent; // 维持保证金百分比
    private String requiredMarginPercent; // 所需保证金百分比
    private String baseAsset; // 基础资产，如 BTC
    private String quoteAsset; // 报价资产，如 USD
    private Integer pricePrecision; // 价格精度
    private Integer quantityPrecision; // 数量精度
    private Integer baseAssetPrecision; // 基础资产精度
    private Integer quotePrecision; // 报价资产精度
    private Integer equalQtyPrecision; // 等量精度
    private Integer maxMoveOrderLimit; // 最大移动订单限制
    private String triggerProtect; // 触发保护
    private String underlyingType; // 基础类型，如 COIN
    private List<String> underlyingSubType; // 子类型，如 PoW
//    private List<Filter> filters; // 过滤器
    private List<String> orderTypes; // 订单类型，如 LIMIT, MARKET 等
    private List<String> timeInForce; // 订单有效期类型，如 GTC, IOC 等
    private String liquidationFee; // 清算费率
    private String marketTakeBound; // 市场成交界限
}
