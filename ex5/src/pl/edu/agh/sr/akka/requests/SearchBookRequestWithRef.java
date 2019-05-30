package pl.edu.agh.sr.akka.requests;

import akka.actor.ActorRef;
import pl.edu.agh.sr.akka.model.Book;

public class SearchBookRequestWithRef extends SearchBookRequest {
    private ActorRef ref;

    public SearchBookRequestWithRef(Book book, ActorRef ref) {
        super(book);
        this.ref = ref;
    }

    public ActorRef getRef() {
        return ref;
    }
}
