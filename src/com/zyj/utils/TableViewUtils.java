package com.zyj.utils;

import com.zyj.config.inject.FieldName;
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
     * @param grid
     * @param clazz
     */
    public static void  openTableViewDragDrop(TableView tableView) {
        tableView.setRowFactory(tv->{
            TableRow row = new TableRow<>();
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
                    Object o = tableView.getItems().remove(draggedIndex);

                    int dropIndex;
                    if (row.isEmpty()) {
                        dropIndex = tableView.getItems().size();
                    } else {
                        dropIndex = row.getIndex();
                    }

                    tableView.getItems().add(dropIndex,  o);

                    event.setDropCompleted(true);
                    tableView.getSelectionModel().select(dropIndex);
                    event.consume();
                }
            });
            return  row;
        });
    }
}
