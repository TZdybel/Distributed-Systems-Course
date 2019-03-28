package jgroups;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.protocols.*;
import org.jgroups.protocols.pbcast.*;
import org.jgroups.stack.ProtocolStack;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

public class DistributedMap implements SimpleStringMap {
    private Map<String, Integer> distributedMapLocalCopy;
    private JChannel channel;

    public DistributedMap(String channelName) throws Exception {
        this.distributedMapLocalCopy = new HashMap<>();
        this.channel = new JChannel(false);

        ProtocolStack stack=new ProtocolStack();
        channel.setProtocolStack(stack);
        stack.addProtocol(new UDP().setValue("mcast_group_addr", InetAddress.getByName("230.100.200.7")))
                .addProtocol(new PING())
                .addProtocol(new MERGE3())
                .addProtocol(new FD_SOCK())
                .addProtocol(new FD_ALL().setValue("timeout", 12000).setValue("interval", 3000))
                .addProtocol(new VERIFY_SUSPECT())
                .addProtocol(new BARRIER())
                .addProtocol(new NAKACK2())
                .addProtocol(new UNICAST3())
                .addProtocol(new STABLE())
                .addProtocol(new GMS())
                .addProtocol(new UFC())
                .addProtocol(new MFC())
                .addProtocol(new FRAG2())
                .addProtocol(new STATE());
        stack.init();

        channel.setReceiver(new Receiver(this, channel));
        channel.connect(channelName, null, 0);
    }

    @Override
    public boolean containsKey(String key) {
        return distributedMapLocalCopy.containsKey(key);
    }

    @Override
    public Integer get(String key) {
        return distributedMapLocalCopy.get(key);
    }

    @Override
    public void put(String key, Integer value) {
        try {
            channel.send(new Message(null, null, String.format("put %s %d", key, value)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        distributedMapLocalCopy.put(key, value);
    }

    @Override
    public Integer remove(String key) {
        try {
            channel.send(new Message(null, null, String.format("remove %s", key)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return distributedMapLocalCopy.remove(key);
    }

    public void setState(Map<String, Integer> map) {
        distributedMapLocalCopy.clear();
        distributedMapLocalCopy.putAll(map);
    }

    public String handleMessage(String msg) {
        String[] message = msg.split(" ");
        switch(message[0]) {
            case "contains":
                return String.valueOf(this.containsKey(message[1]));
            case "get":
                return String.valueOf(this.get(message[1]));
            case "put":
                this.put(message[1], Integer.parseInt(message[2]));
                return "1";
            case "remove":
                return String.valueOf(this.remove(message[1]));
            default:
                System.out.println("Invalid message");
        }
        return null;
    }

    public void updateLocalMap(String msg) {
        String[] message = msg.split(" ");
        switch(message[0]) {
            case "put":
                distributedMapLocalCopy.put(message[1], Integer.parseInt(message[2]));
                break;
            case "remove":
                distributedMapLocalCopy.remove(message[1]);
                break;
            default:
                System.out.println("Invalid message");
        }
    }

    public Map<String, Integer> getDistributedMapLocalCopy() {
        return distributedMapLocalCopy;
    }
}
