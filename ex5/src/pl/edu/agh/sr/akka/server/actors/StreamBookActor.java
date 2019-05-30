package pl.edu.agh.sr.akka.server.actors;

import akka.Done;
import akka.NotUsed;
import akka.actor.AbstractActor;
import akka.stream.ActorMaterializer;
import akka.stream.Materializer;
import akka.stream.javadsl.*;
import akka.util.ByteString;
import pl.edu.agh.sr.akka.requests.StreamRequest;
import pl.edu.agh.sr.akka.responses.StreamResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Path;
import java.util.concurrent.CompletionStage;

import static java.lang.Thread.sleep;

public class StreamBookActor extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(StreamRequest.class, req -> {
                    String title = req.getBook().getTitle();
                    File bookFile = new File("src/pl/edu/agh/sr/akka/resources/books/" + title + ".txt");
                    if (bookFile.exists()) {
                        final Materializer mat = ActorMaterializer.create(context().system());
                        final Path file = bookFile.toPath();
                        Source<Path, NotUsed> source = Source.single(file);
                        final Flow<ByteString, ByteString, NotUsed> flow = Flow.of(ByteString.class)
                                .via(Framing.delimiter(ByteString.fromString("\n"), 256, FramingTruncation.ALLOW));
                        final Sink<ByteString, CompletionStage<Done>> sink = Sink.foreach(s -> {
                            context().parent().tell(new StreamResponse(s), null);
                            sleep(1000);
                        });
                        FileIO.fromPath(file).via(flow).runWith(sink, mat);
                    } else throw new FileNotFoundException();
                })
                .match(StreamResponse.class, resp -> context().parent().tell(resp, null))
                .matchAny(o -> System.out.println("Unknown message"))
                .build();
    }
}
