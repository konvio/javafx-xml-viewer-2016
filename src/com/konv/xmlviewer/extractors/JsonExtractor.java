package com.konv.xmlviewer.extractors;

import com.konv.xmlviewer.model.BookModel;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonExtractor implements Extractor {

    @Override
    public List<BookModel> extract(File file) throws Exception {
        List<BookModel> result = new ArrayList<>();
        JSONArray booksJson = new JSONArray(new JSONTokener(new FileInputStream(file)));
        for (Object obj : booksJson) {
            JSONObject bookJson = (JSONObject) obj;
            BookModel bookModel = new BookModel();
            bookModel.setISBN(bookJson.getInt("isbn"));
            bookModel.setTitle(bookJson.getString("title"));
            bookModel.setAuthor(bookJson.getString("author"));
            bookModel.setAnnotation(bookJson.getString("annotation"));
            bookModel.setReader(bookJson.getString("reader"));
            bookModel.setPrice(bookJson.getInt("price"));
            result.add(bookModel);
        }
        return result;
    }
}
