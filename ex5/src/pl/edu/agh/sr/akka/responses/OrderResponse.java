package pl.edu.agh.sr.akka.responses;

import akka.util.ByteString;

public class OrderResponse extends Response {
    public OrderResponse(ByteString response) {
        super(response);
    }
}
