package pl.edu.agh.sr.akka.client;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import pl.edu.agh.sr.akka.model.Book;
import pl.edu.agh.sr.akka.requests.OrderRequest;
import pl.edu.agh.sr.akka.requests.SearchBookRequest;
import pl.edu.agh.sr.akka.requests.StreamRequest;
import pl.edu.agh.sr.akka.responses.OrderResponse;
import pl.edu.agh.sr.akka.responses.SearchBookResponse;
import pl.edu.agh.sr.akka.responses.StreamResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Client extends AbstractActor {
    private String server = "akka.tcp://server@127.0.0.1:9000/user/server";

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StreamRequest.class, req -> getContext().actorSelection(server).tell(req, getSelf()))
                .match(StreamResponse.class, resp -> System.out.println(resp.getResponse().utf8String()))
                .match(OrderRequest.class, req -> getContext().actorSelection(server).tell(req, getSelf()))
                .match(OrderResponse.class, resp -> System.out.println(resp.getResponse().utf8String()))
                .match(SearchBookRequest.class, req -> getContext().actorSelection(server).tell(req, getSelf()))
                .match(SearchBookResponse.class, resp -> System.out.println(resp.getResponse().utf8String()))
                .matchAny(o -> System.out.println("Unknown message"))
                .build();
    }

    public static void main(String[] args) throws Exception {
        File configFile = new File("src/pl/edu/agh/sr/akka/config/client_conf.conf");
        Config config = ConfigFactory.parseFile(configFile);

        final ActorSystem system = ActorSystem.create("client", config);
        final ActorRef actor = system.actorOf(Props.create(Client.class), "client");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            String line = br.readLine();
            if (line.startsWith("stream") && line.split(" ").length > 1) {
                Book book = new Book(String.join(" ", Arrays.asList(line.split(" ")).subList(1, line.split(" ").length)));
                actor.tell(new StreamRequest(book), null);
            } else if (line.startsWith("order") && line.split(" ").length > 1) {
                Book book = new Book(String.join(" ", Arrays.asList(line.split(" ")).subList(1, line.split(" ").length)));
                actor.tell(new OrderRequest(book), null);
            } else if (line.startsWith("search") && line.split(" ").length > 1) {
                Book book = new Book(String.join(" ", Arrays.asList(line.split(" ")).subList(1, line.split(" ").length)));
                actor.tell(new SearchBookRequest(book), null);
            } else if (line.equals("q")){
                break;
            } else {
                System.out.println("Unknown message");
            }
        }
        system.terminate();
    }
}
