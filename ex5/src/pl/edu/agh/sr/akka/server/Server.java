package pl.edu.agh.sr.akka.server;

import akka.actor.*;
import akka.japi.pf.DeciderBuilder;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import pl.edu.agh.sr.akka.requests.OrderRequest;
import pl.edu.agh.sr.akka.requests.SearchBookRequest;
import pl.edu.agh.sr.akka.requests.StreamRequest;
import pl.edu.agh.sr.akka.server.actors.BookOrderActor;
import pl.edu.agh.sr.akka.server.actors.BookSearchActor;
import pl.edu.agh.sr.akka.server.actors.StreamBookActor;
import scala.concurrent.duration.Duration;

import java.io.*;

import static akka.actor.SupervisorStrategy.restart;
import static akka.actor.SupervisorStrategy.resume;

public class Server extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StreamRequest.class, req -> context().child("streamBookActor").get().tell(req, sender()))
                .match(OrderRequest.class, req -> context().child("orderBookActor").get().tell(req, sender()))
                .match(SearchBookRequest.class, req -> context().child("bookSearchActor").get().tell(req, sender()))
                .matchAny(o -> System.out.println("Unknown message"))
                .build();
    }

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(StreamBookActor.class), "streamBookActor");
        context().actorOf(Props.create(BookOrderActor.class), "orderBookActor");
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

    public static void main(String[] args) throws Exception{
        File configFile = new File("src/pl/edu/agh/sr/akka/config/server_conf.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem system = ActorSystem.create("server", config);
        final ActorRef actor = system.actorOf(Props.create(Server.class), "server");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.equals("q")) {
                break;
            }
            actor.tell(line, null);
        }
        system.terminate();
    }
}
