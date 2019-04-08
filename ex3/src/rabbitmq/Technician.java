package rabbitmq;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.Scanner;
import java.util.concurrent.TimeoutException;

import static java.lang.System.exit;

//wykonuje badania, wysyła wyniki
public class Technician {
    private static final String logExchangeName = "admin";
    private static final String logKey = "admin";
    private static final String infoKey = "info";
    private static String infoQueue;

    private static ConnectionFactory factory = new ConnectionFactory();
    private static Connection connection;
    private static Channel channel;
    private static final String exchangeName = "examination";

    private static String queueName1, queueName2;

    private static boolean checkIfInjuryNameIsRight(String name) {
        return name.equals("knee") || name.equals("hip") || name.equals("elbow");
    }

    private static String getProperInjuryName() {
        Scanner scanner = new Scanner(System.in);
        String injury = scanner.nextLine();
        while (!checkIfInjuryNameIsRight(injury)) {
            System.out.println("Injury name has to be one of: knee, hip, elbow");
            injury = scanner.nextLine();
        }
        return injury;
    }

    private static void init() throws Exception {
        factory.setHost("localhost");
        connection = factory.newConnection();
        channel = connection.createChannel();
        channel.exchangeDeclare(exchangeName, BuiltinExchangeType.DIRECT);

        System.out.println("Enter what injuries technician will handle: ");
        queueName1 = getProperInjuryName();
        queueName2 = getProperInjuryName();
        while (queueName1.equals(queueName2)) {
            System.out.println("Injury types have to be different");
            queueName2 = getProperInjuryName();
        }

        channel.queueDeclare(queueName1, false, false, false, null);
        channel.queueDeclare(queueName2, false, false, false, null);
        channel.queueBind(queueName1, exchangeName, queueName1);
        channel.queueBind(queueName2, exchangeName, queueName2);
        infoQueue = channel.queueDeclare().getQueue();
        channel.queueBind(infoQueue, logExchangeName, infoKey);
        channel.basicQos(1);
    }

    public static void main(String[] args) throws Exception{
        init();

        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println("Received: " + message);
                String[] info = message.split(" ");
                String resultExchangeName = "results";

                try {
                    Thread.sleep(2000);
                } catch (Exception e) {
                    e.printStackTrace();
                    exit(1);
                }

                if (!message.equals("INFO MESSAGE")) {
                    String resultMessage = String.format("%s %s %s", info[1], envelope.getRoutingKey(), "done");
                    channel.basicPublish(resultExchangeName, info[0], null, resultMessage.getBytes("UTF-8"));
                    channel.basicPublish(logExchangeName, logKey, null, resultMessage.getBytes("UTF-8"));
                    System.out.println("Sent: " + resultMessage);
                }
            }
        };

        System.out.println(String.format("Ready to work with %s and %s", queueName1, queueName2));
        channel.basicConsume(queueName1, true, consumer);
        channel.basicConsume(queueName2, true, consumer);
        channel.basicConsume(infoQueue, true, consumer);
    }
}
