package com.konv.xmlviewer;

import com.konv.xmlviewer.extractors.Extractor;
import com.konv.xmlviewer.extractors.JaxbExtractor;
import com.konv.xmlviewer.extractors.JsonExtractor;
import com.konv.xmlviewer.model.BookModel;
import com.konv.xmlviewer.model.LibraryModel;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Controller {

    @FXML
    private TableView<BookModel> mTableView;
    private final ObservableList<BookModel> mItems = FXCollections.observableArrayList();
    private Extractor mExtractor;
    private FileChooser mFileChooser;
    private File mFile;

    @FXML
    private TextField mTitleField;
    @FXML
    private TextField mAuthorField;
    @FXML
    private TextField mAnnotationField;
    @FXML
    private TextField mReaderField;
    @FXML
    private TextField mPriceField;

    @FXML
    private void initialize() {
        initTableView();
        initExtractor();
        initFileChooser();
    }

    @FXML
    private void open() {
        File file = mFileChooser.showOpenDialog(new Stage());
        open(file);
    }

    @FXML
    private void refresh() {
        open(mFile);
    }

    private void open(File file) {
        if (file != null) {
            try {
                List<BookModel> books;
                if (file.getName().endsWith(".json")) books = new JsonExtractor().extract(file);
                else books = mExtractor.extract(file);
                mItems.clear();
                mItems.addAll(filter(books));
                mFile = file;
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
                new FileChooser.ExtensionFilter("XML", "*.xml"), new FileChooser.ExtensionFilter("JSON", "*.json"));
    }

    @FXML
    private void showAbout() {
        DialogUtils.showAlert(Alert.AlertType.INFORMATION, "About", null,
                "XML Viewer\n\n" + "Copyright © 2016 by Vitaliy Kononenko\nK-24");
    }

    public List<BookModel> filter(List<BookModel> source) {
        Predicates predicates = new Predicates();
        return source.stream()
                .filter(predicates.titlePredicate)
                .filter(predicates.authorPredicate)
                .filter(predicates.readerPredicate)
                .filter(predicates.annotationsPredicate)
                .filter(predicates.pricePredicate)
                .collect(Collectors.toList());
    }

    public class Predicates {
        Predicate<BookModel> titlePredicate = bookModel ->
                contains(bookModel.getTitle(), mTitleField.getText());
        Predicate<BookModel> authorPredicate = bookModel ->
                contains(bookModel.getAuthor(), mAuthorField.getText());
        Predicate<BookModel> readerPredicate = bookModel ->
                contains(bookModel.getReader(), mReaderField.getText());
        Predicate<BookModel> annotationsPredicate = bookModel ->
                contains(bookModel.getAnnotation(), mAnnotationField.getText());
        Predicate<BookModel> pricePredicate = bookModel -> {
            try {
                double d = Double.parseDouble(mPriceField.getText());
                return bookModel.getPrice() > d;
            } catch (Exception e) {
                return true;
            }
        };

        private boolean contains(String text, String word) {
            return "".equals(word) || text.toLowerCase().contains(word.toLowerCase());
        }
    }
}
