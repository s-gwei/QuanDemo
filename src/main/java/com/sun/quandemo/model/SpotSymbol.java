package com.sun.quandemo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

/**
 * 币安现货交易对信息
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpotSymbol {
    /**
     * 交易对名称
     */
    private String symbol;

    /**
     * 基础资产（交易对中的第一个币种），如 BTC。
     */
    private String baseAsset;

    /**
     * quoteAsset：报价资产（交易对中的第二个币种），如 USDT。
     */
    private String quoteAsset;

    /**
     * 交易对状态（TRADING-交易中，BREAK-暂停交易等）
     */
    private String status;

    /**
     *      订单类型：
     * 	•	LIMIT：限价单。
     * 	•	MARKET：市价单。
     * 	•	其他订单类型可能包括 STOP_LOSS、TAKE_PROFIT 等。
     */
    private List<String> orderTypes;

    /**
     * 是否允许现货交易
     */
    private Boolean isSpotTradingAllowed;

    /**
     * 是否允许保证金交易
     */
    private Boolean isMarginTradingAllowed;

    /**
     * 价格精度
     */
    private Integer quotePrecision;

    /**
     * 合约类型
     */
    private String contractType;

    /**
     * 数量精度
     */
    private Integer baseAssetPrecision;

     /*
     {
  "timezone": "UTC",
  "serverTime": 1672531200000,
  "symbols": [
    {
      "symbol": "BTCUSDT",
      "status": "TRADING",
      "baseAsset": "BTC",
      "baseAssetPrecision": 8,
      "quoteAsset": "USDT",
      "quotePrecision": 8,
      "quoteAssetPrecision": 8,
      "baseCommissionPrecision": 8,
      "quoteCommissionPrecision": 8,
      "orderTypes": ["LIMIT", "MARKET"],
      "icebergAllowed": true,
      "ocoAllowed": true,
      "isSpotTradingAllowed": true,
      "isMarginTradingAllowed": false,
      "filters": [
        {
          "filterType": "PRICE_FILTER",
          "minPrice": "0.01",
          "maxPrice": "1000000",
          "tickSize": "0.01"
        },
        {
          "filterType": "LOT_SIZE",
          "minQty": "0.0001",
          "maxQty": "1000",
          "stepSize": "0.0001"
        },
        {
          "filterType": "MIN_NOTIONAL",
          "minNotional": "10"
        }
      ]
    }
  ]
}
      */

    /*
    symbols（数组内字段）
	1.	symbol：交易对的名称，例如 BTCUSDT 表示 BTC/USDT。
	2.	status：交易对的状态。
	•	TRADING：表示交易对正在交易。
	•	其他状态可能表示暂停、下架等。
	3.	baseAsset：基础资产（交易对中的第一个币种），如 BTC。
	4.	baseAssetPrecision：基础资产的精度，表示小数点后的位数。
	5.	quoteAsset：报价资产（交易对中的第二个币种），如 USDT。
	6.	quotePrecision：报价资产的精度。
	7.	quoteAssetPrecision：报价资产的精度（与 quotePrecision 通常相同）。
	8.	baseCommissionPrecision 和 quoteCommissionPrecision：分别表示基础资产和报价资产的佣金精度。
	9.	orderTypes：支持的订单类型：
	•	LIMIT：限价单。
	•	MARKET：市价单。
	•	其他订单类型可能包括 STOP_LOSS、TAKE_PROFIT 等。
	10.	icebergAllowed：是否支持冰山订单（隐藏部分挂单数量）。
	11.	ocoAllowed：是否支持 OCO（One Cancels the Other，一撤一单）。
	12.	isSpotTradingAllowed：是否允许现货交易。
	13.	isMarginTradingAllowed：是否允许保证金交易。
     */
} 