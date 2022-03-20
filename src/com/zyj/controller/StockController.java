package com.zyj.controller;

import com.alibaba.fastjson.JSONObject;
import com.zyj.Main;
import com.zyj.utils.*;
import com.zyj.vo.StockVo;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.util.*;

public class StockController extends ScheduledService<Void> implements Initializable  {

    @FXML
    public TableView<StockVo> tableView;
    @FXML
    private TextField stockCode;
    @FXML
    public ScrollPane pane;

    private static StockController stockController;

    public static StockController getStockController() {
        return stockController;
    }

    public static void setStockController(StockController stockController2) {
        stockController = stockController2;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        TableViewUtils.initTableView(tableView,StockVo.class);
        TableViewUtils.openTableViewDragDrop(tableView);
        setStockController(this);
        handleRefreshAction();
    }

    /**
     * 添加
     */
    @FXML
    private void handleInsertAction(){
        CommonUtils.confVo.getStocks().add(new StockVo(stockCode.getText().toUpperCase(),CommonUtils.confVo.getStocks().size()));
        handleRefreshAction();
    }

    /**
     * 删除
     */
    @FXML
    private void handleDeleteAction(){
        List<StockVo> stockVoList=tableView.getSelectionModel().getSelectedItems();
        CommonUtils.confVo.getStocks().remove(stockVoList);
        List<StockVo> confStockVos = CommonUtils.confVo.getStocks();
        for (int i = 0; i < confStockVos.size(); i++) {
            StockVo confStockVo = confStockVos.get(i);
            for (StockVo stockVo : stockVoList) {
                if(confStockVo.getStockCode().equals(stockVo.getStockCode())){
                    confStockVos.remove(confStockVo);
                    i++;
                }
            }
        }
        FileUtils.writeFileContent(JSONObject.toJSONString(CommonUtils.confVo));
        tableView.getItems().setAll(StockUtils.getStock(CommonUtils.confVo.getStocks(),true));
    }

    /**
     * 刷新
     */
    @FXML
    private void handleRefreshAction(){
        tableView.getItems().setAll(StockUtils.getStock(CommonUtils.confVo.getStocks(),false));
    }

    /**
     * 置顶
     */
    @FXML
    private void handleTopAction(){
        Main.isTop=!Main.isTop;
        Main.primaryStageStatic.setAlwaysOnTop(Main.isTop);
    }



    @Override
    protected Task createTask() {
        return new Task<Void>() {
            @Override
            protected Void call() {
                if(DateUtils.getBetweenNowDateTime()){
                    handleRefreshAction();
                }
                return null;
            }
        };
    }
}
