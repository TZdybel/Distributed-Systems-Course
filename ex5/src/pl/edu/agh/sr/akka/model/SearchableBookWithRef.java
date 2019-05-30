package pl.edu.agh.sr.akka.model;

import akka.actor.ActorRef;

public class SearchableBookWithRef extends SearchableBook {
    private ActorRef ref;

    public SearchableBookWithRef(String title, String database, ActorRef ref) {
        super(title, database);
        this.ref = ref;
    }

    public ActorRef getRef() {
        return ref;
    }
}
