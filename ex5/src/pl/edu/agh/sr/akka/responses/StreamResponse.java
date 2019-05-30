package pl.edu.agh.sr.akka.responses;

import akka.util.ByteString;

public class StreamResponse extends Response {
    public StreamResponse(ByteString response) {
        super(response);
    }
}
