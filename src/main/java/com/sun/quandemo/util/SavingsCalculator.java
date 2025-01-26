package com.sun.quandemo.util;

public class SavingsCalculator {

    public static void main(String[] args) {
        // 初始存款50万
        double initialDeposit = 500000;
        // 年收益率6%
        double annualInterestRate = 0.06;
        // 前三年每年存款20万
        double yearlyDepositFirst3Years = 200000;
        // 后面每年存款12万
        double yearlyDepositAfter3Years = 120000;
        // 总周期20年
        int totalYears = 20;

        // 存款初始值
        double totalSavings = initialDeposit;

        // 打印初始存款
        System.out.println("Year 0: " + String.format("%.2f", totalSavings / 10000) + "万");

        // 计算每年存款变化
        for (int year = 1; year <= totalYears; year++) {
            // 根据年数选择不同的存款金额
            if (year <= 3) {
                totalSavings += yearlyDepositFirst3Years;
            } else {
                totalSavings += yearlyDepositAfter3Years;
            }

            // 计算复利
            totalSavings *= (1 + annualInterestRate);

            // 打印每年末的存款，单位为“万”，保留两位小数
            System.out.println("Year " + year + ": " + String.format("%.2f", totalSavings / 10000) + "万");
        }
    }
}
