package com.zyj.vo;

import com.zyj.config.inject.FieldName;

/**
 * 股票
 */
public class StockVo {

    /**
     * 股票代码
     */
//    @FieldName("股票代码")
    private String stockCode;

    /**
     * 股票名称
     */
    @FieldName(value = "名称",width = 70)
    private String stockName;

    /**
     * 价格
     */
    @FieldName(value = "价格",width = 60)
    private String price;
    /**
     * 幅度
     */
    @FieldName(value = "幅度",width = 60)
    private String range;

    public StockVo(String stockCode, String stockName, String price, String range) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.price = price;
        this.range = range;
    }

    public StockVo(String stockCode) {
        this.stockCode = stockCode;
    }

    @Override
    public String toString() {
        return "StockVo{" +
                "stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", price='" + price + '\'' +
                ", range='" + range + '\'' +
                '}';
    }

    public String getStockCode() {
        return stockCode;
    }

    public void setStockCode(String stockCode) {
        this.stockCode = stockCode;
    }

    public String getStockName() {
        return stockName;
    }

    public void setStockName(String stockName) {
        this.stockName = stockName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }
}
