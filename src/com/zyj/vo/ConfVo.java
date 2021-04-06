package com.zyj.vo;

import java.util.List;

/**
 * 配置文件
 */
public class ConfVo {

    private List<StockVo> stocks;

    private Long interval;

    private String token;

    private Double opacity;
    private Double positionX;
    private Double positionY;

    public Double getPositionX() {
        return positionX;
    }

    public void setPositionX(Double positionX) {
        this.positionX = positionX;
    }

    public Double getPositionY() {
        return positionY;
    }

    public void setPositionY(Double positionY) {
        this.positionY = positionY;
    }

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

    public Double getOpacity() {
        return opacity;
    }

    public void setOpacity(Double opacity) {
        this.opacity = opacity;
    }
}
