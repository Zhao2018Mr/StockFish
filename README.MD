##小窗口缩略版股票摸鱼.
###javafx 项目
运行 [mian方法](https://github.com/Zhao2018Mr/StockFish/blob/master/src/com/zyj/Main.java)

###配置文件说明
```json
{
    "interval":15000,
    "stocks":[
        {
            "price":"3414.4",
            "range":"-0.16%",
            "stockCode":"SH000001",
            "stockName":"上证指数"
        }
    ],
    "token":"xq_a_token=a4b3e3e158cfe9745b677915691ecd794b4bf2f9;"
}
```
1. interval: 间隔时长,默认多长时间刷新一次收益率
2. stocks: 股票内容
3. token: 雪球网站的token值