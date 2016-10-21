package co.edu.icesi.ketal.test.distribution;

import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;
import org.jgroups.util.Util;

import java.util.List;
import java.util.LinkedList;
import java.util.Random;
import java.io.BufferedReader;
import java.io.InputStreamReader;   

public class SimpleChat extends ReceiverAdapter {
    JChannel channel;
    String user_name=System.getProperty("user.name", "n/a");
    final List<String> state=new LinkedList<String>();

    public void viewAccepted(View new_view) {
        System.out.println("** view: " + new_view);
    }

    public void receive(Message msg) {
        String line=msg.getSrc() + ": " + msg.getObject();
        System.out.println(line);
        synchronized(state) {
            state.add(line);
        }
    }

    public byte[] getState() {
        synchronized(state) {
            try {
                return Util.objectToByteBuffer(state);
            }
            catch(Exception e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public void setState(byte[] new_state) {
        try {
            List<String> list=(List<String>)Util.objectFromByteBuffer(new_state);
            synchronized(state) {
                state.clear();
                state.addAll(list);
            }
            System.out.println("received state (" + list.size() + " messages in chat history):");
            for(String str: list) {
                System.out.println(str);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    private void start() throws Exception {
    	
    	String props = "UDP(mcast_send_buf_size=32000;mcast_port=45566;ucast_recv_buf_size=64000;" +
    			   "mcast_addr=228.8.8.8;loopback=true;mcast_recv_buf_size=64000;max_bundle_size=60000;" +
    			   "max_bundle_timeout=30;" +
    			   "ucast_send_buf_size=32000;ip_ttl=32;enable_bundling=false" +
    			   System.getProperty("bind.addr") + 
    			   System.getProperty("bind.port") + "):" +
    			   "PING(timeout=2000;num_initial_members=3):" +
    			   //Added to simulate lost messages
    			   "DISCARD(up=0.05;excludeItself=true):" +
    			   "MERGE2(min_interval=10000;max_interval=20000):" +
    			   "FD(timeout=2000;max_tries=4):" +
    			   "VERIFY_SUSPECT(timeout=1500):" +
    			   "pbcast.NAKACK(use_mcast_xmit=false;retransmit_timeout=600,1200,2400,4800):" +
    			   "UNICAST(timeout=1200,2400,3600):" +
    			   "pbcast.STABLE(stability_delay=1000;desired_avg_gossip=20000;max_bytes=0):" +
    			   "FRAG(frag_size=8192):" +
    			   "pbcast.GMS(join_timeout=5000;print_local_addr=true):Causal";
    	
        channel=new JChannel(props);
        channel.setReceiver(this);
        channel.connect("ChatCluster");
        //channel.getState(null, 10000);
        eventLoop();
        channel.close();
    }

    private void eventLoop() {
        BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
        int cont=0;
        
        while(true) {
            try {
                System.out.print("> "); System.out.flush();
                String line=in.readLine().toLowerCase();
                if(line.startsWith("quit") || line.startsWith("exit")) {
                  break;
                }
                line="[" + user_name + "] " + line;
                Message msg=new Message(null, null, line);
                channel.send(null, null, msg);
            }
            catch(Exception e) {
            }
        }
    }


    public static void main(String[] args) throws Exception {
        new SimpleChat().start();
    }
}
