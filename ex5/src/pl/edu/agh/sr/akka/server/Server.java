package pl.edu.agh.sr.akka.server;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import pl.edu.agh.sr.akka.requests.OrderRequest;
import pl.edu.agh.sr.akka.requests.SearchBookRequest;
import pl.edu.agh.sr.akka.requests.StreamRequest;
import pl.edu.agh.sr.akka.responses.OrderResponse;
import pl.edu.agh.sr.akka.responses.SearchBookResponse;
import pl.edu.agh.sr.akka.responses.StreamResponse;
import pl.edu.agh.sr.akka.server.actors.BookOrderActor;
import pl.edu.agh.sr.akka.server.actors.BookSearchActor;
import pl.edu.agh.sr.akka.server.actors.StreamBookActor;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class Server extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .matchAny(o -> sender().tell(o, null))
                .match(StreamRequest.class, req -> context().child("streamBookActor").get().tell(req, sender()))
                .match(StreamResponse.class, resp -> System.out.println(resp.getResponse().utf8String()))
                .match(OrderRequest.class, req -> context().child("orderBookActor").get().tell(req, sender()))
                .match(OrderResponse.class, resp -> System.out.println(resp.getResponse().utf8String()))
                .match(SearchBookRequest.class, req -> context().child("bookSearchActor").get().tell(req, sender()))
                .match(SearchBookResponse.class, resp -> System.out.println(resp.getResponse().utf8String()))
                .build();
    }

    @Override
    public void preStart() throws Exception {
        context().actorOf(Props.create(StreamBookActor.class), "streamBookActor");
        context().actorOf(Props.create(BookOrderActor.class), "orderBookActor");
        context().actorOf(Props.create(BookSearchActor.class), "bookSearchActor");
    }

    public static void main(String[] args) throws Exception{
        File configFile = new File("src/pl/edu/agh/sr/akka/config/server_conf.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem system = ActorSystem.create("server", config);
        final ActorRef actor = system.actorOf(Props.create(Server.class), "server_actor");

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
