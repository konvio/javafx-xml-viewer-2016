package com.konv.xmlviewer;

import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class DialogUtils {

    public static void showAlert(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(new Image(Main.class.getResourceAsStream("xml-icon.png")));

        alert.showAndWait();
    }

    public static void showExpandableAlert(Alert.AlertType alertType, String title, String header, String content,
                                           String expandableContent) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);

        TextArea textArea = new TextArea(expandableContent);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);

        VBox.setVgrow(textArea, Priority.ALWAYS);
        alert.getDialogPane().setExpandableContent(new VBox(textArea));
        alert.getDialogPane().setExpanded(true);

        alert.showAndWait();
    }
}
