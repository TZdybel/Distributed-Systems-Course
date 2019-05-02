package pl.edu.agh.sr.ExchangeRatesProvider;

import io.grpc.Server;
import io.grpc.ServerBuilder;
import pl.edu.agh.sr.ExchangeRatesProvider.Implementation.ExchangeRatesServiceImpl;

import java.io.IOException;

public class ExchangeRatesServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        int port = 9000;
        Server server = ServerBuilder
                .forPort(port)
                .addService(new ExchangeRatesServiceImpl()).build();

        server.start();
        System.out.println("Server started at localhost with port " + port);
        server.awaitTermination();
    }
}
