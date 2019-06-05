package pl.edu.agh.sr.zookeeper;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataMonitor implements Watcher {
    private ZooKeeper zk;
    private String znode;
    private DataMonitorListener listener;
    private List<String> children;
    private boolean dead;

    public DataMonitor(ZooKeeper zk, String znode, DataMonitorListener listener) {
        this.zk = zk;
        this.znode = znode;
        this.listener = listener;
        if (watchNode() != null) {
            this.listener.nodeCreated();
        }
        children = getChildrenNodes();
    }

    public interface DataMonitorListener {
        void nodeCreated();
        void nodeDeleted();
        void newChild();
        void childDeleted();
        void closing();
    }

    private Stat watchNode() {
        try {
            return zk.exists(znode, this);
        } catch (KeeperException | InterruptedException e) {
            return null;
        }
    }

    private List<String> getChildrenNodes() {
        try {
//            return zk.getChildren(znode, this);
            List<String> list = new ArrayList<>();
            list.add(znode);
            int index = 0;
            while(index < list.size()){
                String path = list.get(index);
                List<String> tempChildren = zk.getChildren(path, this);
                if(!path.equalsIgnoreCase("/")){
                    path = path + "/";
                }
                for(int i = tempChildren.size() - 1; i >= 0; i--){
                    list.add(index + 1, path + tempChildren.get(i));
                }
                index++;
            }
            return list;
        } catch (Exception e) {
            return null;
        }
    }

    public void process(WatchedEvent event) {
        switch (event.getType()) {
            case None:
                switch (event.getState()) {
                    case SyncConnected:
                        break;
                    case Expired:
                        dead = true;
                        listener.closing();
                        break;
                }
                break;
            case NodeCreated:
                listener.nodeCreated();
                watchNode();
                children = getChildrenNodes();
                break;
            case NodeDeleted:
                if (event.getPath().equals(znode)) {
                    listener.nodeDeleted();
                    watchNode();
                    children = getChildrenNodes();
                    break;
                }
            case NodeChildrenChanged:
                if (getChildrenNodes() != null) {
                    if (children.size() > getChildrenNodes().size()) listener.childDeleted();
                    else if (children.size() < getChildrenNodes().size()) listener.newChild();
                }
                children = getChildrenNodes();
                break;
        }
    }

    public boolean isDead() {
        return dead;
    }
}