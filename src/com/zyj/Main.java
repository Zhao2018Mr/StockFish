package com.zyj;

import com.zyj.controller.StockController;
import com.zyj.utils.CommonUtils;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.util.concurrent.Executors;

public class Main extends Application {

    public static Stage primaryStageStatic = null;
    /**
     * 是否置顶
     */
    public static boolean isTop = false;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = null;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("stock.fxml"));
        primaryStageStatic = primaryStage;
        root = loader.load();
        primaryStage.setTitle("Fishing");
        primaryStage.setOpacity(CommonUtils.confVo.getOpacity());
        primaryStage.setAlwaysOnTop(isTop);
        primaryStage.initStyle(StageStyle.UTILITY);
        Scene scene = new Scene(root, 210, 400);
        scene.setFill(null);
        primaryStage.setScene(scene);
        primaryStage.show();
        executeScheduledService();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        System.exit(0);
    }




    private void executeScheduledService(){
        StockController testScheduledService = StockController.getStockController();
        // 设置线程池，restart会尝试重用线程
        testScheduledService.setExecutor(Executors.newFixedThreadPool(1));
        // 延时2s开始
        testScheduledService.setDelay(Duration.millis(2000));
        // 间隔1s执行
        testScheduledService.setPeriod(Duration.millis(CommonUtils.confVo.getInterval()));
        testScheduledService.start();
        // delay和period的设置时间经过之后，ScheduledService Delay Timer线程才会靠cancel()结束
//        testScheduledService.cancel();
//        testScheduledService.restart();
    }







}
