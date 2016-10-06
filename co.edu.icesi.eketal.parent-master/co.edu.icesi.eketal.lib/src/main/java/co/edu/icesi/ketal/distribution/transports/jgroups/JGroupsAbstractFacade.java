package co.edu.icesi.ketal.distribution.transports.jgroups;

import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.util.RspList;

import co.edu.icesi.ketal.distribution.EventBroker;

/**
 * Contains the channel that is used to send sync or async messages through the
 * network
 * 
 * @author dduran
 * 
 */
public abstract class JGroupsAbstractFacade extends ReceiverAdapter {

	// Default logger
	static org.apache.log4j.Logger logger = org.apache.log4j.Logger
			.getLogger(JGroupsAbstractFacade.class);

	// Channel object, this is part of Jgroups API
	Channel channel;
	RpcDispatcher disp;
	RspList rsp_list;
	RequestOptions opts;
	// RequestHandler disp;

	// The properties configuring the Jgroups communication stack
	// Properties are managed by class DistributionProperties
	//String props = DistributionProperties.getInstance()
			//.getStack("AspectsGroup");
	  private String props = "UDP(mcast_send_buf_size=32000;mcast_port=45566;ucast_recv_buf_size=64000;" +
			   "mcast_addr=228.8.8.8;mcast_recv_buf_size=64000;max_bundle_size=60000;" +
			   "max_bundle_timeout=30;" +
			   "ucast_send_buf_size=32000;ip_ttl=32):" +
			   "PING(timeout=2000;num_initial_members=3):" +
			   //Added to simulate lost messages
			   "DISCARD(up=0.05;excludeItself=true):" +
			   "MERGE2(min_interval=10000;max_interval=20000):" +
			   "FD(timeout=2000;max_tries=4):" +
			   "VERIFY_SUSPECT(timeout=1500):" +
			   "pbcast.NAKACK(use_mcast_xmit=false;retransmit_timeout=600,1200,2400,4800):" +
			   "pbcast.STABLE(stability_delay=1000;desired_avg_gossip=20000;max_bytes=0):" +
			   "FRAG(frag_size=8192):" +
			   "pbcast.GMS(join_timeout=5000;print_local_addr=true;view_bundling=true):Causal(causal_order_prot_interest=false)";

	String groupName;
	EventBroker jeb;
	static int typeOfMsgSent = 0;

	private Object signal = new Object();

	/**
	 * Method that contains the instructions to initialize the channel and the
	 * messages receiver
	 */
	public abstract void initializeFacade();

	/**
	 * Initializes the channel and receives the base group name and the specific
	 * EventBroker (to handle the messages) as parameters
	 * 
	 * @param groupName
	 * @param jeb
	 */
	public JGroupsAbstractFacade(String groupName, EventBroker jeb) {
		this.groupName = groupName;
		this.jeb = jeb;
		try {
			channel = new JChannel(props);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void closeComunication(){
		if(channel==null)
			return;
		channel.disconnect();
		channel.close();
	}
	
	/**
	 * Initializes the channel and receives the base group name as a parameter
	 * 
	 * @param groupName
	 */
	public JGroupsAbstractFacade(String groupName) {
		this.groupName = groupName;
		try {
			channel = new JChannel();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static org.apache.log4j.Logger getLogger() {
		return logger;
	}

	public static void setLogger(org.apache.log4j.Logger logger) {
		JGroupsAbstractFacade.logger = logger;
	}

	public Channel getChannel() {
		return channel;
	}

	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public RpcDispatcher getDisp() {
		return disp;
	}

	public void setDisp(RpcDispatcher disp) {
		this.disp = disp;
	}

	public RspList getRsp_list() {
		return rsp_list;
	}

	public void setRsp_list(RspList rsp_list) {
		this.rsp_list = rsp_list;
	}

	public RequestOptions getOpts() {
		return opts;
	}

	public void setOpts(RequestOptions opts) {
		this.opts = opts;
	}

	public String getProps() {
		return props;
	}

	public void setProps(String props) {
		this.props = props;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public EventBroker getJeb() {
		return jeb;
	}

	public void setJeb(EventBroker jeb) {
		this.jeb = jeb;
	}

	public static int getTypeOfMsgSent() {
		return typeOfMsgSent;
	}

	public static void setTypeOfMsgSent(int typeOfMsgSent) {
		JGroupsAbstractFacade.typeOfMsgSent = typeOfMsgSent;
	}

	public Object getSignal() {
		return signal;
	}

	public void setSignal(Object signal) {
		this.signal = signal;
	}
	
	@Override
	protected void finalize() throws Throwable {
		closeComunication();
		super.finalize();
	}
}
