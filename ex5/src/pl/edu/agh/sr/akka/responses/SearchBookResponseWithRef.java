package pl.edu.agh.sr.akka.responses;

import akka.actor.ActorRef;
import akka.util.ByteString;

public class SearchBookResponseWithRef extends SearchBookResponse {
    private ActorRef ref;

    public SearchBookResponseWithRef(ByteString response, ActorRef ref) {
        super(response);
        this.ref = ref;
    }

    public ActorRef getRef() {
        return ref;
    }
}
