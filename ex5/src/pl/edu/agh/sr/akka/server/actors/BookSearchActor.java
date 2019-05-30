package pl.edu.agh.sr.akka.server.actors;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import pl.edu.agh.sr.akka.model.SearchableBook;
import pl.edu.agh.sr.akka.model.SearchableBookWithRef;
import pl.edu.agh.sr.akka.requests.SearchBookRequest;
import pl.edu.agh.sr.akka.requests.SearchBookRequestWithRef;
import pl.edu.agh.sr.akka.responses.SearchBookResponse;
import scala.concurrent.duration.Duration;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

public class BookSearchActor extends AbstractActor {
    private List<String> databases = Arrays.asList("src/pl/edu/agh/sr/akka/resources/databases/database1.txt", "src/pl/edu/agh/sr/akka/resources/databases/database2.txt");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchBookRequestWithRef.class, req -> {
                    int counter = 1;
                    for (String db: databases) {
                        SearchableBookWithRef book = new SearchableBookWithRef(req.getBook().getTitle(), db, req.getRef());
                        context().child(String.format("searchWorker%d", counter)).get().tell(book, sender());
                        counter++;
                    }
                })
                .match(SearchBookRequest.class, req -> {
                    int counter = 1;
                    for (String db: databases) {
                        SearchableBook book = new SearchableBook(req.getBook().getTitle(), db);
                        context().child(String.format("searchWorker%d", counter)).get().tell(book, sender());
                        counter++;
                    }
                })
                .matchAny(o -> System.out.println("Unknown message"))
                .build();
    }

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(BookSearchWorker.class), "searchWorker1");
        context().actorOf(Props.create(BookSearchWorker.class), "searchWorker2");
    }

    private static SupervisorStrategy strategy = new OneForOneStrategy(10, Duration.create("1 minute"),
            DeciderBuilder.match(FileNotFoundException.class, o -> resume())
                    .match(IOException.class, o -> resume())
                    .matchAny(o -> restart())
                    .build());

    @Override
    public SupervisorStrategy supervisorStrategy() {
        return strategy;
    }
}
