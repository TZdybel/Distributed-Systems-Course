package pl.edu.agh.sr.akka.requests;

import pl.edu.agh.sr.akka.model.Book;

import java.io.Serializable;

public class Request implements Serializable {
    private Book book;

    public Request(Book book) {
        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
