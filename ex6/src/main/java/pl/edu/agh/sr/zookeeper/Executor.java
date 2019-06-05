package pl.edu.agh.sr.zookeeper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

public class Executor implements Watcher, Runnable, DataMonitor.DataMonitorListener {

    private DataMonitor dm;
    private ZooKeeper zk;
    private Process child;
    private FileOutputStream outputStream = new FileOutputStream("/Users/tzdybel/Studia/SR/zookeeper_zad/stream.txt");
    private String hostport;
    private String znode;

    public Executor(String hostPort, String znode) throws IOException {
        this.znode = znode;
        this.hostport = hostPort;
        zk = new ZooKeeper(hostPort, 3000, this);
        dm = new DataMonitor(zk, znode, this);
        System.out.println(znode);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("USAGE: Executor host:port znode");
            System.exit(1);
        }
        String hostPort = args[0];
        String znode = args[1];
        try {
            new Executor(hostPort, znode).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void process(WatchedEvent event) {
        dm.process(event);
    }

    public void run() {
        try {
            synchronized (this) {
                while (!dm.isDead()) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void nodeCreated() {
        if (child == null) {
            try {
                System.out.println("Create process");
                child = Runtime.getRuntime().exec("java -cp /Users/tzdybel/Studia/SR/zookeeper_zad/build/class" +
                        "es/java/main/:/Users/tzdybel/.gradle/caches/modules-2/files-2.1/org.apache.zookeeper/zookeeper/" +
                        "3.5.5/dd9c924e9d4be7c79e46261691e96d030736a8ac/zookeeper-3.5.5.jar:/Users/tzdybel/.gradle/caches" +
                        "/modules-2/files-2.1/org.apache.zookeeper/zookeeper-jute/3.5.5/3785011a665bd5c7dedd025110543d967f1" +
                        "7f8e3/zookeeper-jute-3.5.5.jar:/Users/tzdybel/.gradle/caches/modules-2/files-2.1/org.slf4j/slf4j" +
                        "-log4j12/1.7.25/110cefe2df103412849d72ef7a67e4e91e4266b4/slf4j-log4j12-1.7.25.jar:/Users/tzdybel/" +
                        ".gradle/caches/modules-2/files-2.1/org.slf4j/slf4j-api/1.7.25/da76ca59f6a57ee3102f8f9bd9cee742973e" +
                        "fa8a/slf4j-api-1.7.25.jar:/Users/tzdybel/.gradle/caches/modules-2/files-2.1/log4j/log4j/1.2.17/5af3" +
                        "5056b4d257e4b64b9e8069c0746e8b08629f/log4j-1.2.17.jar pl.edu.agh.sr.zookeeper.App " + hostport + " " + znode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void nodeDeleted() {
        if (child != null) {
            System.out.println("Destroy process");
            child.destroy();
            try {
                child.waitFor();
                PrintWriter writer = new PrintWriter("/Users/tzdybel/Studia/SR/zookeeper_zad/stream.txt");
                writer.print("");
                writer.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        child = null;
    }

    @Override
    public void newChild() {
        System.out.println("Added new child");
        try {
            outputStream.write("+".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void childDeleted() {
        System.out.println("Deleted child");
        try {
            outputStream.write("-".getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closing() {
        synchronized (this) {
            notifyAll();
        }
    }
}
