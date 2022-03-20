package com.zyj.vo;

import com.zyj.config.inject.FieldName;

/**
 * 股票
 */
public class StockVo implements Comparable<StockVo>{

    /**
     * 股票代码
     */
//    @FieldName("股票代码")
    private String stockCode;

    /**
     * 股票名称
     */
    @FieldName(value = "MC",width = 70)
    private String stockName;

    /**
     * 价格
     */
    @FieldName(value = "JG",width = 60)
    private String price;
    /**
     * 幅度
     */
    @FieldName(value = "FD",width = 60)
    private String range;

    /**
     * 排序
     */
    private Integer orderNum;

    public StockVo(String stockCode, String stockName, String price, String range,Integer orderNum) {
        this.stockCode = stockCode;
        this.stockName = stockName;
        this.price = price;
        this.range = range;
        this.orderNum = orderNum;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public StockVo(String stockCode,Integer orderNum) {
        this.stockCode = stockCode;
        this.orderNum = orderNum;
    }

    @Override
    public String toString() {
        return "StockVo{" +
                "stockCode='" + stockCode + '\'' +
                ", stockName='" + stockName + '\'' +
                ", price='" + price + '\'' +
                ", range='" + range + '\'' +
                ", orderNum=" + orderNum +
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



    @Override
    public int compareTo(StockVo stockVo) {
        if(this.orderNum==null || stockVo.getOrderNum()==null){
            return -99;
        }
        return this.orderNum - stockVo.getOrderNum();
    }
}
