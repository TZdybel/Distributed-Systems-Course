package pl.edu.agh.sr.akka.responses;

import akka.util.ByteString;

import java.io.Serializable;

public class Response implements Serializable {
    private ByteString response;

    public Response(ByteString response) {
        this.response = response;
    }

    public ByteString getResponse() {
        return response;
    }
}
