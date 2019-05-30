package pl.edu.agh.sr.akka.server.actors;

import akka.actor.AbstractActor;
import akka.util.ByteString;
import pl.edu.agh.sr.akka.requests.OrderRequest;
import pl.edu.agh.sr.akka.responses.OrderResponse;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

public class BookOrderActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(OrderRequest.class, req -> {
                    File orders = new File("src/pl/edu/agh/sr/akka/resources/orders/orders.txt");
                    if (!orders.exists()) orders.createNewFile();
                    BufferedWriter writer = new BufferedWriter(new FileWriter(orders, true));
                    writer.write(req.getBook().getTitle() + "\n");
                    writer.close();
                    sender().tell(new OrderResponse(ByteString.fromString("Order for " + req.getBook().getTitle() + " accepted")), null);
                })
                .matchAny(o -> System.out.println("Unknown message"))
                .build();
    }
}
