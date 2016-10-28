package com.konv.xmlviewer;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.ArrayList;
import java.util.List;

public class Controller {

    @FXML
    private TableView mTableView;

    @FXML
    private void initialize() {
        initTableView();
    }

    private void initTableView() {
        List<TableColumn> columns = new ArrayList<>();
        columns.add(new TableColumn("ISBN"));
        columns.add(new TableColumn("Title"));
        columns.add(new TableColumn("Author"));
        columns.add(new TableColumn("Annotation"));
        columns.add(new TableColumn("Reader"));
        columns.add(new TableColumn("Price"));

        mTableView.getColumns().addAll(columns);
    }
}
