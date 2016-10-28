package com.konv.xmlviewer.extractors;

import com.konv.xmlviewer.model.BookModel;

import java.io.File;
import java.util.List;

@FunctionalInterface
public interface Extractor {

    List<BookModel> extract(File file) throws Exception;
}
