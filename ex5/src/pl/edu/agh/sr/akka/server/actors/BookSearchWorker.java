package pl.edu.agh.sr.akka.server.actors;

import akka.actor.AbstractActor;
import akka.util.ByteString;
import pl.edu.agh.sr.akka.model.SearchableBook;
import pl.edu.agh.sr.akka.responses.SearchBookResponse;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;

public class BookSearchWorker extends AbstractActor {
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(SearchableBook.class, req -> {
                    String title = req.getTitle();
                    String dbPath = req.getDatabase();
                    File file = new File(dbPath);
                    if (file.exists()) {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        String line = reader.readLine();
                        while (line != null) {
                            String foundTitle = String.join(" ", Arrays.asList(line.split(" ")).subList(0, line.split(" ").length - 1));
                            if (foundTitle.trim().equals(title)) {
                                context().parent().tell(new SearchBookResponse(ByteString.fromString(line)), null);
                                break;
                            }
                            line = reader.readLine();
                        }
                        reader.close();
                    } else throw new FileNotFoundException();
                })
                .matchAny(o -> System.out.println("Unknown message"))
                .build();
    }
}
