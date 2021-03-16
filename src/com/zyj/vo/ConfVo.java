package com.zyj.vo;

import java.util.List;

/**
 * 配置文件
 */
public class ConfVo {

    private List<StockVo> stocks;

    private Long interval;

    private String token;

    public List<StockVo> getStocks() {
        return stocks;
    }

    public void setStocks(List<StockVo> stocks) {
        this.stocks = stocks;
    }

    public ConfVo() {
    }

    public ConfVo(List<StockVo> stocks) {
        this.stocks = stocks;
    }

    public Long getInterval() {
        return interval;
    }

    public void setInterval(Long interval) {
        this.interval = interval;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
