package com.zyj.utils.httpclient;

import com.zyj.utils.CommonUtils;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author : zyj
 * @version : 1.0
 * @date : 2020-7-7 09:45:49
 */
public class HttpClientUtil {

    /**
     * 通过post方式调用http接口
     * @param url     url路径
     * @param jsonParam    json格式的参数
     * @param reSend     重发次数
     * @return
     * @throws Exception
     */
    public static String sendPostByJson(String url, String jsonParam,int reSend) {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime=System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime= 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            Header header=new BasicHeader("Accept-Encoding",null);
            httpPost.setHeader(header);
            // 设置报文和通讯格式
            if(jsonParam!=null){
                StringEntity stringEntity = new StringEntity(jsonParam,HttpConstant.UTF8_ENCODE);
                stringEntity.setContentEncoding(HttpConstant.UTF8_ENCODE);
                stringEntity.setContentType(HttpConstant.APPLICATION_JSON);
                httpPost.setEntity(stringEntity);
            }
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity= httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            if (reSend > 0) {
                result = sendPostByJson(url, jsonParam, reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
            }
        }
        //请求接口的响应时间
        endTime=System.currentTimeMillis();
        return result;
    }


    public  static String sendHttpGet(String url,int reSend)  {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime=System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime= 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            HttpGet httpGet = HttpClientFactory.getInstance().httpGet(url);
            httpGet.setHeader("cookie", CommonUtils.confVo.getToken());
            // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了

            httpResponse = httpClient.execute(httpGet);
            httpEntity = httpResponse.getEntity();
            // 通过EntityUtils 来将我们的数据转换成字符串
            result = EntityUtils.toString(httpEntity,"UTF-8");
        } catch (Exception e) {
            if (reSend > 0) {
                result = sendHttpGet(url,  reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
            }
        }
        endTime=System.currentTimeMillis();
        return result;
    }

    public static void main(String[] args) {
        System.out.println(sendHttpGetIndex());;
    }


    /**
     * 获取cookie的值
     * @author zyj
     * @date 2021/9/1 11:15
     * @return java.lang.String
     */
    public  static String sendHttpGetIndex()  {
        String url = "https://xueqiu.com/";
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime=System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime= 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            HttpGet httpGet = HttpClientFactory.getInstance().httpGet(url);
            httpGet.setHeader("cookie", CommonUtils.confVo.getToken());
            // 通过client调用execute方法，得到我们的执行结果就是一个response，所有的数据都封装在response里面了

            httpResponse = httpClient.execute(httpGet);
            Header[] headers = httpResponse.getHeaders("Set-Cookie");
            for (Header header : headers) {
                for (HeaderElement element : header.getElements()) {
                    if(element.getName().equals("xq_a_token")){
                        return "xq_a_token="+ element.getValue();
                    }
                }
            }
        } catch (Exception e) {
            if (HttpConstant.REQ_TIMES > 0) {
                result = sendHttpGetIndex();
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
            }
        }
        endTime=System.currentTimeMillis();
        return result;
    }

    /**
     * 通过post方式调用http接口
     * @param url     url路径
     * @param map    json格式的参数
     * @param reSend     重发次数
     * @return
     * @throws Exception
     */
    public static String sendPostByForm(String url, Map<String,String> map,int reSend) {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime=System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime= 0L;
        HttpEntity httpEntity = null;
        UrlEncodedFormEntity entity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);

            //设置参数
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            Iterator iterator = map.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String,String> elem = (Map.Entry<String, String>) iterator.next();
                list.add(new BasicNameValuePair(elem.getKey(),elem.getValue()));
            }
            entity = new UrlEncodedFormEntity(list,HttpConstant.UTF8_ENCODE);
            httpPost.setEntity(entity);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity= httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } catch (Exception e) {
            if (reSend > 0) {
                result = sendPostByForm(url, map, reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        }finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
            }
        }
        //请求接口的响应时间
        endTime=System.currentTimeMillis();
        return result;

    }
    /**
     * 通过post方式调用http接口
     * @param url     url路径
     * @param xmlParam    json格式的参数
     * @param reSend     重发次数
     * @return
     * @throws Exception
     */
    public static String sendPostByXml(String url, String xmlParam,int reSend) {
        //声明返回结果
        String result = "";
        //开始请求API接口时间
        long startTime = System.currentTimeMillis();
        //请求API接口的响应时间
        long endTime = 0L;
        HttpEntity httpEntity = null;
        HttpResponse httpResponse = null;
        HttpClient httpClient = null;
        try {
            // 创建连接
            httpClient = HttpClientFactory.getInstance().getHttpClient();
            // 设置请求头和报文
            HttpPost httpPost = HttpClientFactory.getInstance().httpPost(url);
            StringEntity stringEntity = new StringEntity(xmlParam, HttpConstant.UTF8_ENCODE);
            stringEntity.setContentEncoding(HttpConstant.UTF8_ENCODE);
            stringEntity.setContentType(HttpConstant.TEXT_XML);
            httpPost.setEntity(stringEntity);
            //执行发送，获取相应结果
            httpResponse = httpClient.execute(httpPost);
            httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity,HttpConstant.UTF8_ENCODE);
        } catch (Exception e) {
            if (reSend > 0) {
                result = sendPostByJson(url, xmlParam, reSend - 1);
                if (result != null && !"".equals(result)) {
                    return result;
                }
            }
        } finally {
            try {
                EntityUtils.consume(httpEntity);
            } catch (IOException e) {
            }
            //请求接口的响应时间
            endTime = System.currentTimeMillis();
            return result;
        }
    }

}