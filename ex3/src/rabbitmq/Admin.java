package rabbitmq;

import com.rabbitmq.client.*;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

//loguje aktywnosc, moze wysylac do wszystkich info
public class Admin {
    private static Connection connection;
    private static Channel channel;
    private static final String exchangeName = "admin";
    private static final String queueName = "admin";
    private static final String infoKey = "info";

    private static BufferedWriter writer;

    private static void init() throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);

        channel.queueDeclare(queueName, false, false, false, null);
        channel.queueBind(queueName, exchangeName, queueName);

        writer = new BufferedWriter(new FileWriter("log.log"));
    }

    public static void main(String[] args) throws Exception {
        init();

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("LOG -- " + message);
                writer.write("LOG -- " + message + "\n");
                writer.flush();
            }
        };

        Thread infoSender = new Thread(new InfoSender());
        infoSender.start();
        channel.basicConsume(queueName, true, consumer);
    }

    private static class InfoSender implements Runnable {
        @Override
        public void run() {
            System.out.println("TO SEND INFO MESSAGE WRITE 'INFO'");
            while (true) {
                Scanner scanner = new Scanner(System.in);
                String info = scanner.nextLine();
                if (info.equals("INFO")) sendInfo();
            }
        }
    }

    private static void sendInfo() {
        try {
            channel.basicPublish(exchangeName, infoKey, null, "INFO MESSAGE".getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
