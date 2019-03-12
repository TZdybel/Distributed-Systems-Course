import java.io.*;
import java.net.*;

public class MulticastLogger {
    public static void main(String[] args) {
        MulticastSocket socket = null;
        byte[] buffer = new byte[1024];
        BufferedWriter writer = null;
        try {
            socket = new MulticastSocket(9000);
            socket.joinGroup(InetAddress.getByName("230.0.0.0"));
            writer = new BufferedWriter(new FileWriter("log.txt"));
            System.out.println("Logger ready");
            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String msg = new String(packet.getData());
                System.out.println(msg);
                writer.write(msg);
                writer.write("\n");
                writer.flush();
            }
        } catch (IOException e) {
            System.out.println("An error occured");
        } finally {
            try {
                if (socket != null) socket.close();
                if (writer != null) writer.close();
            } catch (IOException e) {
                System.out.println("IOException while closing resources");
            }
        }
    }
}
