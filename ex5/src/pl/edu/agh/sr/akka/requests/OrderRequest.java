package pl.edu.agh.sr.akka.requests;

import pl.edu.agh.sr.akka.model.Book;

public class OrderRequest extends Request {
    public OrderRequest(Book book) {
        super(book);
    }
}
