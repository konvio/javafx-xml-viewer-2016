package com.konv.xmlviewer.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "library")
public class LibraryModel {

    private List<BookModel> books;

    public List<BookModel> getBooks() {
        return books;
    }

    @XmlElement(name = "book")
    public void setBooks(List<BookModel> books) {
        this.books = books;
    }
}
