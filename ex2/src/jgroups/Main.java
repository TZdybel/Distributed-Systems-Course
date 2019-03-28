package jgroups;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws Exception{
        System.setProperty("java.net.preferIPv4Stack","true");
        DistributedMap map = new DistributedMap("mapa");
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Enter command: ");
            String msg = scanner.nextLine();
            System.out.println("Response: " + map.handleMessage(msg));
        }
    }
}
