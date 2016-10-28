package com.konv.xmlviewer.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "Book")
public class BookModel {

    private SimpleIntegerProperty ISBN = new SimpleIntegerProperty(0);
    private SimpleStringProperty title = new SimpleStringProperty("Unnamed");
    private SimpleStringProperty author = new SimpleStringProperty("No name");
    private SimpleStringProperty annotation = new SimpleStringProperty("");
    private SimpleStringProperty reader = new SimpleStringProperty("");
    private SimpleIntegerProperty price = new SimpleIntegerProperty(0);

    public int getISBN() {
        return ISBN.get();
    }

    @XmlElement(name = "isbn")
    public void setISBN(int ISBN) {
        this.ISBN.set(ISBN);
    }

    public String getTitle() {
        return title.get();
    }

    @XmlElement(name = "title")
    public void setTitle(String title) {
        this.title.set(title);
    }

    public String getAuthor() {
        return author.get();
    }

    @XmlElement(name = "author")
    public void setAuthor(String author) {
        this.author.set(author);
    }

    public String getAnnotation() {
        return annotation.get();
    }

    @XmlElement(name = "annotation")
    public void setAnnotation(String annotation) {
        this.annotation.set(annotation);
    }

    public String getReader() {
        return reader.get();
    }

    @XmlElement(name = "reader")
    public void setReader(String reader) {
        this.reader.set(reader);
    }

    public int getPrice() {
        return price.get();
    }

    @XmlElement(name = "price")
    public void setPrice(int price) {
        this.price.set(price);
    }

    @Override
    public String toString() {
        return String.format("%s %s %s", author, title, price);
    }
}
