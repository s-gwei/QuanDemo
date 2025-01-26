package com.sun.quandemo.service;

import com.sun.quandemo.constant.BinanceApiConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.time.Instant;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;

/**
 * 币安现货市场服务类
 */
@Service
@Slf4j
public class SpotMarketServiceImpl {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 获取指定交易对的24小时交易历史数据
     * @param symbol 交易对名称（例如：BTCUSDT）
     * @return 24小时交易历史数据
     */
    public String get24HourHistory(String symbol) {
        String params = "?symbol=" + symbol.toUpperCase();
        String url = BinanceApiConstants.buildUrl(
            BinanceApiConstants.SPOT_DOMAIN,
            BinanceApiConstants.SPOT_TICKER_24HR,
            params
        );
        
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        return response.getBody();
    }

    /**
     * 检查交易对是否有超过3个月的交易历史
     * @param symbol 交易对名称（例如：BTCUSDT）
     * @return true如果交易历史超过3个月，false如果不足3个月或发生错误
     */
    public boolean hasThreeMonthsHistory(String symbol) {
        try {
            // 获取当前时间戳（毫秒）
            long endTime = Instant.now().toEpochMilli();
            // 3个月前的时间戳
            long startTime = endTime - (90L * 24 * 60 * 60 * 1000);
            
            // 构建请求参数
            String params = String.format("?symbol=%s&interval=1d&startTime=%d&endTime=%d&limit=1",
                    symbol.toUpperCase(), startTime, endTime);
            
            String url = BinanceApiConstants.buildUrl(
                BinanceApiConstants.SPOT_DOMAIN,
                BinanceApiConstants.SPOT_KLINES,
                params
            );
            
            ResponseEntity<List> response = restTemplate.getForEntity(url, List.class);
            List<List<Object>> klines = response.getBody();
            
            // 如果能获取到K线数据，且第一个K线的时间戳接近3个月前，说明有足够的历史
            if (klines != null && !klines.isEmpty()) {
                Long firstKlineTime = Long.parseLong(klines.get(0).get(0).toString());
                // 允许有1天的误差
                return Math.abs(firstKlineTime - startTime) < (24 * 60 * 60 * 1000);
            }
            
            return false;
        } catch (Exception e) {
            // 发生异常时返回false
            return false;
        }
    }
} 