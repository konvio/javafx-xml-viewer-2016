package com.konv.xmlviewer;

import com.konv.xmlviewer.extractors.Extractor;
import com.konv.xmlviewer.extractors.JaxbExtractor;
import com.konv.xmlviewer.model.BookModel;
import com.konv.xmlviewer.model.LibraryModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class Controller {

    @FXML
    private TableView<BookModel> mTableView;
    private final ObservableList<BookModel> mItems = FXCollections.observableArrayList();
    private Extractor mExtractor;
    private FileChooser mFileChooser;

    @FXML
    private void initialize() {
        initTableView();
        initExtractor();
        initFileChooser();
    }

    @FXML
    private void open() {
        File file = mFileChooser.showOpenDialog(new Stage());
        try {
            List<BookModel> books = mExtractor.extract(file);
            mItems.clear();
            mItems.addAll(books);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @SuppressWarnings("unchecked")
    private void initTableView() {
        TableColumn<BookModel, Integer> isbnColumn = new TableColumn<>("ISBN");
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));

        TableColumn<BookModel, String> titleColumn = new TableColumn<>("Title");
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));

        TableColumn<BookModel, String> authorColumn = new TableColumn<>("Author");
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));

        TableColumn<BookModel, String> annotationColumn = new TableColumn<>("Annotation");
        annotationColumn.setCellValueFactory(new PropertyValueFactory<>("annotation"));

        TableColumn<BookModel, String> readerColumn = new TableColumn<>("Reader");
        readerColumn.setCellValueFactory(new PropertyValueFactory<>("reader"));

        TableColumn<BookModel, Integer> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        mTableView.getColumns().addAll(isbnColumn, titleColumn, authorColumn, annotationColumn,
                readerColumn, priceColumn);

        mTableView.setItems(mItems);
    }

    private void initExtractor() {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(LibraryModel.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
            mExtractor = new JaxbExtractor(unmarshaller);
        } catch (JAXBException e) {
            DialogUtils.showAlert(Alert.AlertType.ERROR, "XML Viewer", "Internal error", e.getMessage());
            Platform.exit();
        }
    }

    private void initFileChooser() {
        mFileChooser = new FileChooser();
        mFileChooser.setTitle("XML Viewer");
        mFileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        mFileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("XML", "*.xml"));
    }
}
