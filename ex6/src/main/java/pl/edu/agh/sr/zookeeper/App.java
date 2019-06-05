package pl.edu.agh.sr.zookeeper;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.apache.zookeeper.ZooKeeper;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class App extends Application {
    private static Text counter = new Text("0");
    private static Text tree = new Text("");

    private static ZooKeeper zk;
    private static String znode;

    @Override
    public void init() {
        try {
            zk = new ZooKeeper(getParameters().getUnnamed().get(0), 3000, null);
            znode = getParameters().getUnnamed().get(1);
            setTree(getTree(zk, znode));
            setCounter(getTree(zk, znode).length - 1);
            new Thread(new UpdateApp(new FileInputStream("/Users/tzdybel/Studia/SR/zookeeper_zad/stream.txt"))).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Zookeeper app");
        counter.setX(100);
        counter.setY(100);
        counter.setFont(new Font("Times New Roman", 30));
        tree.setX(20);
        tree.setY(200);
        tree.setFont(new Font("Times New Roman", 20));
        Group root = new Group(counter);
        root.getChildren().add(tree);
        Scene scene = new Scene(root, 200, 400);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private static int getCounter() {
        return Integer.parseInt(counter.getText());
    }

    private static void setCounter(int count) {
        Platform.runLater(() -> counter.setText(count + ""));
    }

    private static void setTree(String[] array) {
        StringBuilder builder = new StringBuilder();
        for (String s : array) {
            builder.append(s);
            builder.append("\n");
        }
        Platform.runLater(() -> tree.setText(builder.toString()));
    }

    private static String[] getTree(ZooKeeper zk, String path) throws Exception{
        if(zk.exists(path, false) == null){
            return new String[0];
        }
        List<String> dealList = new ArrayList<>();
        dealList.add(path);
        int index = 0;
        while(index < dealList.size()){
            String tempPath = dealList.get(index);
            List<String> children = zk.getChildren(tempPath, false);
            if(!tempPath.equalsIgnoreCase("/")){
                tempPath = tempPath + "/";
            }
            Collections.sort(children);
            for(int i = children.size() - 1; i >= 0; i--){
                dealList.add(index + 1, tempPath + children.get(i));
            }
            index++;
        }
        return dealList.toArray(new String[0]);
    }

    class UpdateApp implements Runnable {
        private FileInputStream inputStream;

        public UpdateApp(FileInputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public void run() {
            while (true) {
                try {
                    int x = inputStream.read();
                    if (x != -1) {
                        if ((char)x == '-') setCounter(getCounter() - 1);
                        else if ((char)x == '+') setCounter(getCounter() + 1);
                        setTree(getTree(zk, znode));
                    }
                    Thread.sleep(100);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        String host = args[0];
        String znode = args[1];
        Application.launch(App.class, host, znode);
    }
}
