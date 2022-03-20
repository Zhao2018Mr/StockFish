package com.zyj.utils;

import com.alibaba.fastjson.JSONObject;
import com.zyj.config.inject.FieldName;
import com.zyj.vo.StockVo;
import javafx.collections.ObservableList;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;

import java.lang.reflect.Field;
import java.util.List;

public class TableViewUtils {


    private static final DataFormat SERIALIZED_MIME_TYPE = new DataFormat("application/x-java-serialized-object");


    //初始化tableView
    public static void  initTableView(TableView grid,Class clazz) {
        grid.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        grid.setEditable(true);
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if(field.isAnnotationPresent(FieldName.class)){
                FieldName fieldName = field.getAnnotation(FieldName.class);
                TableColumn col = new TableColumn(fieldName.value());
                col.setCellValueFactory(
                        new PropertyValueFactory<>(field.getName()));
                col.setMinWidth(20);
                col.setPrefWidth(fieldName.width());
                col.setStyle("-fx-font-size: 15");
                grid.getColumns().add(col);
            }
        }
    }

    /**
     * 开启tableView拖拽排序
     */
    public static void  openTableViewDragDrop(TableView<StockVo> tableView) {
        tableView.setRowFactory(tv->{
            TableRow<StockVo> row = new TableRow<>();
            //拖拽-检测
            row.setOnDragDetected(event -> {
                if (!row.isEmpty()) {
                    Integer index = row.getIndex();
                    Dragboard db = row.startDragAndDrop(TransferMode.MOVE);
                    db.setDragView(row.snapshot(null, null));
                    ClipboardContent cc = new ClipboardContent();
                    cc.put(SERIALIZED_MIME_TYPE, index);
                    db.setContent(cc);
                    event.consume();
                }
            });
            //释放-验证
            row.setOnDragOver(event -> {
                Dragboard db = event.getDragboard();
				/* check if same table view
                    TableRow<Person> rowTemp = (TableRow<Person>) event.getGestureSource();
                    if (rowTemp.getTableView() != row.getTableView()) {
				}*/
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    if (row.getIndex() != ((Integer) db.getContent(SERIALIZED_MIME_TYPE)).intValue()) {
                        event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                        event.consume();
                    }
                }
            });
            //释放-执行
            row.setOnDragDropped(event -> {
                Dragboard db = event.getDragboard();
                if (db.hasContent(SERIALIZED_MIME_TYPE)) {
                    int draggedIndex = (Integer) db.getContent(SERIALIZED_MIME_TYPE);
                    StockVo stockVo = tableView.getItems().remove(draggedIndex);
                    int dropIndex;
                    if (row.isEmpty()) {
                        dropIndex = tableView.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    tableView.getItems().add(dropIndex,  stockVo);

                    event.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    // 操作完成
                    event.consume();
                    saveSort(tableView);

                }
            });
            return  row;
        });
    }

    /**
     * 保存当前排序
     */
    public static void saveSort(TableView<StockVo> tableView){
        ObservableList<StockVo> items = tableView.getItems();
        for (int i = 0; i < items.size(); i++) {
            items.get(i).setOrderNum(i);
        }
        // 重新添加
        CommonUtils.confVo.setStocks(items);
        // 重新写入文件
        FileUtils.writeFileContent(JSONObject.toJSONString(CommonUtils.confVo));
    }
}
