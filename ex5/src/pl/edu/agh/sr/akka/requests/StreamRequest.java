package pl.edu.agh.sr.akka.requests;

import pl.edu.agh.sr.akka.model.Book;

public class StreamRequest extends Request {
    public StreamRequest(Book book) {
        super(book);
    }
}
