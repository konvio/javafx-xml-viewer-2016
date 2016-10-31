package com.konv.xmlviewer;

import com.konv.xmlviewer.extractors.Extractor;
import com.konv.xmlviewer.extractors.JaxbExtractor;
import com.konv.xmlviewer.model.BookModel;
import com.konv.xmlviewer.model.LibraryModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Text;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.awt.*;
import java.io.File;
import java.util.List;

public class Controller {

    @FXML
    private TableView<BookModel> mTableView;
    private final ObservableList<BookModel> mItems = FXCollections.observableArrayList();
    private Extractor mExtractor;
    private FileChooser mFileChooser;

    @FXML
    private TextField mTitleField;
    @FXML
    private TextField mAuthorField;
    @FXML
    private TextField mAnnotationField;
    @FXML
    private TextField mReaderField;
    @FXML
    private TextField mPrice;

    @FXML
    private void initialize() {
        initTableView();
        initExtractor();
        initFileChooser();
        mPrice.getText();
    }

    @FXML
    private void open() {
        File file = mFileChooser.showOpenDialog(new Stage());
        open(file);
    }

    private void open(File file) {
        if (file != null) {
            try {
                List<BookModel> books = mExtractor.extract(file);
                mItems.clear();
                mItems.addAll(books);
            } catch (Exception e) {
                DialogUtils.showAlert(Alert.AlertType.INFORMATION, "XML Viewer", "Invalid file structure", e.getLocalizedMessage());
            }
        }
    }

    @FXML
    private void browse() {
        try {
            File result = new File("C:/Users/konv/Desktop/books.html");
            XmlUtils.transformToHtml(mItems, result);
            Desktop.getDesktop().open(result);
        } catch (Exception e) {
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

        for (TableColumn tableColumn : mTableView.getColumns()) tableColumn.setPrefWidth(100);

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
