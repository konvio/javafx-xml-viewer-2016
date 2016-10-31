package com.konv.xmlviewer;

import com.konv.xmlviewer.model.BookModel;
import com.konv.xmlviewer.model.LibraryModel;
import javafx.application.Platform;
import javafx.scene.control.Alert;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.File;
import java.util.List;

public class XmlUtils {

    private static Marshaller mMarshaller;
    private static Transformer mOptimusPrime;

    static {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(LibraryModel.class);
            mMarshaller = jaxbContext.createMarshaller();
            mMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            mOptimusPrime = TransformerFactory.newInstance().newTransformer(
                    new StreamSource(XmlUtils.class.getResourceAsStream("books.xslt")));
        } catch (Exception e) {
            DialogUtils.showAlert(Alert.AlertType.ERROR, "XML Viewer", "Internal error", e.getLocalizedMessage());
            Platform.exit();
        }
    }

    public static void marshall(List<BookModel> books, File file) throws JAXBException {
        LibraryModel libraryModel = new LibraryModel();
        libraryModel.setBooks(books);
        mMarshaller.marshal(libraryModel, file);
    }

    public static void transformToHtml(List<BookModel> books, File resultFile) {
        File temp = new File("temp.xml");
        temp.deleteOnExit();
        try {
            marshall(books, temp);
            mOptimusPrime.transform(new StreamSource(temp), new StreamResult(resultFile));
        } catch (Exception e) {

        }
    }
}
