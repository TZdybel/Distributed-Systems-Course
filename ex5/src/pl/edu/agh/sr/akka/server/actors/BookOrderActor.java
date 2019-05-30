package pl.edu.agh.sr.akka.server.actors;

import akka.actor.AbstractActor;
import akka.actor.OneForOneStrategy;
import akka.actor.Props;
import akka.actor.SupervisorStrategy;
import akka.japi.pf.DeciderBuilder;
import akka.util.ByteString;
import pl.edu.agh.sr.akka.requests.OrderRequest;
import pl.edu.agh.sr.akka.requests.SearchBookRequestWithRef;
import pl.edu.agh.sr.akka.responses.OrderResponse;
import pl.edu.agh.sr.akka.responses.SearchBookResponseWithRef;
import scala.concurrent.duration.Duration;

import java.io.*;
import java.util.Arrays;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

public class BookOrderActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OrderRequest.class, req -> {
                    context().child("bookSearchActor").get().tell(new SearchBookRequestWithRef(req.getBook(), sender()), getSelf());
                })
                .match(SearchBookResponseWithRef.class, resp -> {
                    File orders = new File("src/pl/edu/agh/sr/akka/resources/orders/orders.txt");
                    if (!orders.exists()) orders.createNewFile();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(orders, true));
                    String response = resp.getResponse().utf8String();
                    String title = String.join(" ", Arrays.asList(response.split(" ")).subList(0, response.split(" ").length - 1));
                    writer.write(title + "\n");
                    writer.close();
                    resp.getRef().tell(new OrderResponse(ByteString.fromString("Order for " + title + " accepted")), getSelf());
                })
                .matchAny(o -> System.out.println(o))
                .build();
    }

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(BookSearchActor.class), "bookSearchActor");
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
