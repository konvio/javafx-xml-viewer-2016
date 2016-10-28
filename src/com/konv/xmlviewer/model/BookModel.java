package com.konv.xmlviewer.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Book")
public class BookModel {

    private int ISBN;
    private String title;
    private String author;
    private String annotation;
    private String reader;
    private int price;

    public int getISBN() {
        return ISBN;
    }

    @XmlElement(name = "isbn")
    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    @XmlElement(name = "author")
    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAnnotation() {
        return annotation;
    }

    @XmlElement(name = "annotation")
    public void setAnnotation(String annotation) {
        this.annotation = annotation;
    }

    public String getReader() {
        return reader;
    }

    @XmlElement(name = "reader")
    public void setReader(String reader) {
        this.reader = reader;
    }

    public int getPrice() {
        return price;
    }

    @XmlElement(name = "price")
    public void setPrice(int price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", author, title, price);
    }
}
