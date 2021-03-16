package com.zyj.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyj.utils.httpclient.HttpClientUtil;
import com.zyj.utils.httpclient.HttpConstant;
import com.zyj.vo.StockVo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * 股票代码
 */
public class StockUtils {

    public static final String URL = "https://stock.xueqiu.com/v5/stock/realtime/quotec.json?symbol=";

    public static final String DETAIL_URL = "https://stock.xueqiu.com/v5/stock/quote.json?extend=detail&symbol=";

    public  static Logger logger=Logger.getLogger("StockUtils");

    /**
     * 获取股票数据
     * @param
     * @return
     */
    public static List<StockVo> getStock(List<StockVo> stockVoList,boolean isDel){
        boolean isExitsName =  false;
        StringBuffer stockCodeStringBuffer=new StringBuffer();
        Map<String,StockVo> map=new HashMap();
        for (int i = 0; i < stockVoList.size(); i++) {
            StockVo stockVo=stockVoList.get(i);
            if(stockVo.getStockName()==null || stockVo.getStockName().length()==0){
                String stock = getStockName(stockVo.getStockCode());
                if(stock.equals("")){
                    stockVoList.remove(stockVo);
                    i++;
                    continue;
                }
                stockVo.setStockName(getStockName(stockVo.getStockCode()));
                isExitsName = true;
            }
            stockCodeStringBuffer.append(stockVo.getStockCode());
            stockCodeStringBuffer.append(",");
            map.put(stockVo.getStockCode(),stockVo);
        }
        if(isExitsName || isDel){
            //修改过名字 或删除  重载配置文件
            CommonUtils.confVo.setStocks(stockVoList);
            FileUtils.writeFileContent(JSONObject.toJSONString(CommonUtils.confVo));
        }
        String result =  HttpClientUtil.sendHttpGet(URL+stockCodeStringBuffer, HttpConstant.REQ_TIMES);
        logger.info(result);
        List<StockVo> stockVos=new ArrayList<>();
        JSONArray jsonArray= JSONObject.parseObject(result).getJSONArray("data");
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            StockVo stockVo = map.get(jsonObject.getString("symbol"));
            stockVo.setPrice(jsonObject.getString("current"));
            stockVo.setRange(jsonObject.getString("percent")+"%");
            stockVos.add(stockVo);
        }
        return stockVos;
    }

    /**
     * 获取股票名称
     * @param stockCode
     * @return
     */
    public static String getStockName(String stockCode){
        String result =  HttpClientUtil.sendHttpGet(DETAIL_URL+stockCode, HttpConstant.REQ_TIMES);
        JSONObject jsonObject = JSONObject.parseObject(result).getJSONObject("data").getJSONObject("quote");
        logger.info(result);
        if(jsonObject==null){
            DialogUtil.showError("代码不存在","代码不存在","代码不存在");
            return "";
        }
        return jsonObject.getString("name");
    }
}
