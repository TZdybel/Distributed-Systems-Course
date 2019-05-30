package pl.edu.agh.sr.akka.server.actors;

import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.OverflowStrategy;
import akka.stream.ThrottleMode;
import akka.stream.javadsl.*;
import akka.util.ByteString;
import pl.edu.agh.sr.akka.requests.StreamRequest;
import pl.edu.agh.sr.akka.responses.StreamResponse;
import scala.concurrent.duration.FiniteDuration;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

public class StreamBookActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StreamRequest.class, req -> {
                    String title = req.getBook().getTitle();
                    File bookFile = new File("src/pl/edu/agh/sr/akka/resources/books/" + title + ".txt");
                    if (bookFile.exists()) {
                        final Materializer mat = ActorMaterializer.create(context().system());
                        ActorRef run = Source.actorRef(256, OverflowStrategy.dropNew())
                                .throttle(1, FiniteDuration.create(1, TimeUnit.SECONDS), 1, ThrottleMode.shaping())
                                .to(Sink.actorRef(sender(), NotUsed.getInstance()))
                                .run(mat);

                        Stream<String> lines = Files.lines(bookFile.toPath());
                        lines.forEachOrdered(line -> run.tell(new StreamResponse(ByteString.fromString(line)), getSelf()));
                    } else throw new FileNotFoundException();
                })
                .matchAny(o -> System.out.println("Unknown message"))
                .build();
    }

}
