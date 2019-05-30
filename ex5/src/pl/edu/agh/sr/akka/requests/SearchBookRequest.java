package pl.edu.agh.sr.akka.requests;

import pl.edu.agh.sr.akka.model.Book;

public class SearchBookRequest extends Request {
    public SearchBookRequest(Book book) {
        super(book);
    }
}
