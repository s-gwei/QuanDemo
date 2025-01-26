package com.sun.quandemo.work;

import com.sun.quandemo.constant.BinanceApiConstants;
import com.sun.quandemo.context.BeanSelector;
import com.sun.quandemo.model.CoinContract;
import com.sun.quandemo.model.CoinFundingRate;
import com.sun.quandemo.service.SpotMarketServiceImpl;
import com.sun.quandemo.service.impl.CoinServiceImpl;
import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class CoinBuyWorker extends Thread{

    private static CoinServiceImpl coinServiceImpl = BeanSelector.getBean(CoinServiceImpl.class);
    private static SpotMarketServiceImpl spotMarketServiceImpl = BeanSelector.getBean(com.sun.quandemo.service.SpotMarketServiceImpl.class);

    private static final BigInteger init_money = new BigInteger("10000");

    private static final BigInteger pey_money = new BigInteger("1000");

    public void run(){
        init();
    }

    private void init() {
        //获取可以交易的币本位币对
        List<CoinContract> coinContractList =  coinServiceImpl.getCoinContract();
        if(coinContractList == null || coinContractList.isEmpty()){
            log.info("未获取到币对信息");
        }
        //获取资金费率排行前30的比对
        List<CoinFundingRate> coinFundingRateList = new ArrayList<>();
        int i = 0;
        for(CoinContract coinContract : coinContractList){
            CoinFundingRate coinFundingRate = coinServiceImpl.getCoinFundingRate(coinContract.getSymbol());
            coinFundingRateList.add(coinFundingRate);
            i++;
            if(i >= 30){
                break;
            }
        }
        // 按照 fundingTime 字段降序排列
        coinFundingRateList.sort(Comparator.comparingDouble((CoinFundingRate cfr) -> Double.parseDouble(cfr.getFundingRate())).reversed());

        //获取起现货的上架数据，必须上架3个月以上
        List<CoinFundingRate> copyList1 = new ArrayList<>(coinFundingRateList);
        for(CoinFundingRate coinFundingRate : copyList1){
            boolean flag = spotMarketServiceImpl.hasThreeMonthsHistory(coinFundingRate.getSymbol());
            if(!flag){
                coinFundingRateList.remove(coinFundingRate);
            }
        }

        // 将资金1000u为一份（如果有5wu，则一份为5000u），分别购买前10的币对。
        BigInteger result = init_money.divide(pey_money);
        for (int j = 0; j < result.intValue() ; j ++){
            CoinFundingRate coinFundingRate = coinFundingRateList.get(j);
            //购买当前币对
        }


    }
}
