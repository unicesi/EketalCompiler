package co.edu.icesi.ketal.test.causal;

import java.util.ArrayList;
import java.util.LinkedList;

import org.jgroups.Address;
import org.jgroups.JChannel;
import org.jgroups.Message;
import org.jgroups.ReceiverAdapter;
import org.jgroups.View;

public class NodeCausal extends ReceiverAdapter{
	
	private String nodeName;
	private JChannel channel;
	private Address address;
	private View view;
	private LinkedList<String> lL;	
	private final String prop = "UDP(mcast_send_buf_size=32000;mcast_port=45566;ucast_recv_buf_size=64000;" +
	   "mcast_addr=228.8.8.8;loopback=true;mcast_recv_buf_size=64000;max_bundle_size=60000;" +
	   "max_bundle_timeout=30;" +
	   "ucast_send_buf_size=32000;ip_ttl=32;enable_bundling=false" +
	   System.getProperty("bind.addr") + 
	   System.getProperty("bind.port") + "):" +
"PING(timeout=2000;num_initial_members=3):" +
"MERGE2(min_interval=10000;max_interval=20000):" +
"FD(timeout=2000;max_tries=4):" +
"VERIFY_SUSPECT(timeout=1500):" +
"pbcast.NAKACK(use_mcast_xmit=false;retransmit_timeout=600,1200,2400,4800):" +
"UNICAST(timeout=1200,2400,3600):" +
"pbcast.STABLE(stability_delay=1000;desired_avg_gossip=20000;max_bytes=0):" +
"FRAG(frag_size=8192):" +
"pbcast.GMS(join_timeout=5000;print_local_addr=true):Causal";
	
	public NodeCausal(String name)
	{
		
		try {
			nodeName=name;
			channel= new JChannel(prop);
			lL= new LinkedList<String>();
			this.start();
	        
		} catch (Exception e) {
			
			e.printStackTrace();
		}
	}
	
	public Address getAddressNode()
	{
		return address;
	}
	
	public void viewAccepted(View new_view) {
		view=new_view;
        System.out.println("** view: " + new_view);
    }

	public LinkedList<String> linkedStrings(){
		return lL;
	}
    public void receive(Message msg) {
    	lL.add((String) msg.getObject());
    	System.out.println(nodeName+":  "+ (String)msg.getObject()+":  "+msg.toString());
    }
    public void send(String msg, Address dest) throws Exception
    {
    	channel.send(dest,(Object)msg);	
    }
    private void start() throws Exception {
    	channel.setReceiver(this);
        channel.connect("ChatCluster");
        address=channel.getAddress();
    }
}
