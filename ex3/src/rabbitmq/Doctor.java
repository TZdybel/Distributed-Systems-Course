package rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;

import static java.lang.System.exit;

//zleca badania, dostaje wyniki
public class Doctor {
    private static final String logExchangeName = "admin";
    private static final String logKey = "admin";
    private static final String infoKey = "info";

    private static final Scanner scanner = new Scanner(System.in);
    private static String doctorName;
    private static ConnectionFactory factory = new ConnectionFactory();
    private static Connection connection;
    private static Channel channel;
    private static final String exchangeName = "examination";

    private static void init() throws Exception {
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();

        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
    }

    public static void main(String[] args) throws Exception {
        init();

        System.out.println("Enter doctor name: ");
        doctorName = scanner.nextLine();
        Thread receiver = new Thread(new ReceiveMessage());
        receiver.start();

        while (true) {
            System.out.println("Enter injury name: ");
            String injury = scanner.nextLine();
            if (injury.equals("exit")) break;
            if (!injury.equals("knee") && !injury.equals("hip") && !injury.equals("elbow")) {
                System.out.println("Bad injury name");
                continue;
            }
            System.out.println("Enter client name: ");
            String clientName = scanner.nextLine();
            String message = String.format("%s %s", doctorName, clientName);

            channel.basicPublish(exchangeName, injury, null, message.getBytes("UTF-8"));
            channel.basicPublish(logExchangeName, logKey, null, message.getBytes("UTF-8"));
            System.out.println("Sent: " + message);
        }
    }

    private static class ReceiveMessage implements Runnable {
        @Override
        public void run() {
            String exchangeName = "results";

            try {
                channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);
                channel.queueDeclare(doctorName, false, false, false, null);
                channel.queueDeclare(doctorName + "info", false, false, false, null);
                channel.queueBind(doctorName, exchangeName, doctorName);
                channel.queueBind(doctorName + "info", logExchangeName, infoKey);
            } catch (Exception e) {
                e.printStackTrace();
            }

            Consumer consumer = new DefaultConsumer(channel) {
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    String message = new String(body, "UTF-8");
                    System.out.println("Message: " + message);
                }
            };

            try {
                channel.basicConsume(doctorName, true, consumer);
                channel.basicConsume(doctorName + "info", true, consumer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
