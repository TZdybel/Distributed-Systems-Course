package jgroups;

import org.jgroups.*;
import org.jgroups.util.Util;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Receiver extends ReceiverAdapter {
    private final DistributedMap state;
    private JChannel channel;

    public Receiver(DistributedMap state, JChannel channel) {
        this.state = state;
        this.channel = channel;
    }

    @Override
    public void receive(Message msg) {
        System.out.println("Received message: " + msg.getObject());
        state.updateLocalMap((String)msg.getObject());
    }

    @Override
    public void viewAccepted(View view) {
        if(view instanceof MergeView) {
            ViewHandler handler=new ViewHandler(channel, (MergeView)view);
            // requires separate thread as we don't want to block JGroups
            handler.start();
        }
    }

    @Override
    public void getState(OutputStream output) throws Exception {
        synchronized(state) {
            Util.objectToStream(state.getDistributedMapLocalCopy(), new DataOutputStream(output));
        }
    }

    @Override
    public void setState(InputStream input) throws Exception {
        Map<String, Integer> map;
        map = (HashMap<String, Integer>) Util.objectFromStream(new DataInputStream(input));
        synchronized(state) {
            state.setState(map);
        }
    }

    private static class ViewHandler extends Thread {
        JChannel ch;
        MergeView view;

        private ViewHandler(JChannel ch, MergeView view) {
            this.ch = ch;
            this.view = view;
        }

        public void run() {
            List<View> subgroups = view.getSubgroups();
            View tmp_view = subgroups.get(0); // picks the first
            Address local_addr = ch.getAddress();
            if (!tmp_view.getMembers().contains(local_addr)) {
                System.out.println("Not member of the new primary partition ("
                        + tmp_view + "), will re-acquire the state");
                try {
                    ch.getState(null, 0);
                } catch (Exception ex) {
                }
            } else {
                System.out.println("Member of the new primary partition ("
                        + tmp_view + "), will do nothing");
            }
        }
    }
}
