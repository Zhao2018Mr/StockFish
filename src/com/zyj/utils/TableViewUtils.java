package com.zyj.utils;

import com.zyj.config.inject.FieldName;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.lang.reflect.Field;

public class TableViewUtils {

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
                col.setMinWidth(45);
                col.setPrefWidth(fieldName.width());
                col.setStyle("-fx-font-size: 15");
                grid.getColumns().add(col);
            }
        }
    }
}
