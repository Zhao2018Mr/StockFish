package com.zyj.utils;

import com.alibaba.fastjson.JSONObject;
import com.zyj.utils.httpclient.HttpClientUtil;
import com.zyj.vo.ConfVo;
import com.zyj.vo.StockVo;

import java.util.ArrayList;
import java.util.List;

public class CommonUtils {

    public static  ConfVo confVo= null;


    static {
        String confVoStr = FileUtils.readFileContent();
        if(confVoStr.length()==0){
            confVo=new ConfVo();
            confVo.setStocks(new ArrayList<>());
            confVo.getStocks().add(new StockVo("SH000001",0));
            confVo.setInterval(15000L);
        }else {
            confVoStr = FileUtils.readFileContent();
            confVo= JSONObject.parseObject(confVoStr,ConfVo.class);
            confVo.setStocks(StockUtils.getStock(confVo.getStocks(),false));
            boolean isWrite = false;
            if(confVo.getInterval()==null){
                confVo.setInterval(15000L);
                isWrite = true;
            }
            confVo.setToken(HttpClientUtil.sendHttpGetIndex());
            if(confVo.getToken()==null){
                confVo.setToken("xq_a_token=a4b3e3e158cfe9745b677915691ecd794b4bf2f9;");
                isWrite = true;
            }
            if(confVo.getOpacity()==null){
                confVo.setOpacity(1.0);
                isWrite = true;
            }
            if(isWrite){
                FileUtils.writeFileContent(JSONObject.toJSONString(confVo));
            }
        }
    }




}
