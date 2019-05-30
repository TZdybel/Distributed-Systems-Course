package pl.edu.agh.sr.akka.responses;

import akka.util.ByteString;

public class SearchBookResponse extends Response {
    public SearchBookResponse(ByteString response) {
        super(response);
    }
}
