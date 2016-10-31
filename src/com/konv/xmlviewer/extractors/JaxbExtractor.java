package com.konv.xmlviewer.extractors;

import com.konv.xmlviewer.model.BookModel;
import com.konv.xmlviewer.model.LibraryModel;

import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.util.List;

public class JaxbExtractor implements Extractor {

    private Unmarshaller mUnmarshaller;

    public JaxbExtractor(Unmarshaller unmarshaller) {
        mUnmarshaller = unmarshaller;
    }

    @Override
    public List<BookModel> extract(File file) throws JAXBException {
        LibraryModel libraryModel = (LibraryModel) mUnmarshaller.unmarshal(file);
        return libraryModel.getBooks();
    }
}
