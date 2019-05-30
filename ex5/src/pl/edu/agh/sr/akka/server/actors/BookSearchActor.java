package pl.edu.agh.sr.akka.server.actors;

import akka.actor.AbstractActor;
import akka.actor.Props;
import pl.edu.agh.sr.akka.model.SearchableBook;
import pl.edu.agh.sr.akka.requests.SearchBookRequest;
import pl.edu.agh.sr.akka.responses.SearchBookResponse;

import java.util.Arrays;
import java.util.List;

public class BookSearchActor extends AbstractActor {
    private List<String> databases = Arrays.asList("src/pl/edu/agh/sr/akka/resources/databases/database1.txt", "src/pl/edu/agh/sr/akka/resources/databases/database2.txt");

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchBookRequest.class, req -> {
                    int counter = 1;
                    for (String db: databases) {
                        SearchableBook book = new SearchableBook(req.getBook().getTitle(), db);
                        context().child(String.format("searchWorker%d", counter)).get().tell(book, getSelf());
                        counter++;
                    }
                })
                .match(SearchBookResponse.class, resp -> context().parent().tell(resp, null))
                .matchAny(o -> System.out.println("Unknown message"))
                .build();
    }

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(BookSearchWorker.class), "searchWorker1");
        context().actorOf(Props.create(BookSearchWorker.class), "searchWorker2");
    }
}
