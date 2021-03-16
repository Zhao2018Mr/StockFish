package com.zyj.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Optional;

public class DialogUtil {
    public static void showMessage(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        alert.showAndWait();
    }
    public static void showError(String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }


    //是否确定关闭
    public static boolean choiceClose(){
        ButtonType buttonTypeTYes = new ButtonType("是");
        ButtonType buttonTypeNo = new ButtonType("否");
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getButtonTypes().setAll(buttonTypeTYes, buttonTypeNo);
        alert.setTitle("是否作废");
        alert.setHeaderText("是否作废 ?");
        alert.setContentText("是否作废");
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeTYes){
            return true;
        } else if(result.get() ==buttonTypeNo){
            return  false;
        }else {
            return false;
        }
    }

}
