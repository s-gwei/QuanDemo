package com.sun.quandemo.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)  // 忽略所有未知字段
public class CoinFundingRate {

    private String symbol;
    private long fundingTime;  // 原始时间戳
    private String fundingRate;
    private String markPrice;
    private String formattedFundingTime;  // 格式化后的时间


    // 默认构造函数
    public CoinFundingRate() {
    }

    // 构造函数，使用@JsonCreator和@JsonProperty确保Jackson能够正确映射字段
    @JsonCreator
    public CoinFundingRate(
            @JsonProperty("symbol") String symbol,
            @JsonProperty("fundingTime") long fundingTime,
            @JsonProperty("fundingRate") String fundingRate,
            @JsonProperty("markPrice") String markPrice) {
        this.symbol = symbol;
        this.fundingTime = fundingTime;
        this.fundingRate = fundingRate;
        this.markPrice = markPrice;
        this.formattedFundingTime = formatFundingTime(fundingTime);  // 格式化时间
    }

    // 格式化时间戳为指定的格式
    public static String formatFundingTime(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
        Date date = new Date(timestamp);
        return sdf.format(date);  // 返回格式化后的时间
    }



    @Override
    public String toString() {
        return "CoinFundingRate{" +
                "symbol='" + symbol + '\'' +
                ", fundingTime=" + fundingTime +
                ", fundingRate='" + fundingRate + '\'' +
                ", markPrice='" + markPrice + '\'' +
                ", formattedFundingTime='" + formattedFundingTime + '\'' +
                '}';
    }
}
