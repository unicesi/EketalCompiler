package co.edu.icesi.ketal.distribution.transports.jgroups;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.Channel;
import org.jgroups.ChannelException;
import org.jgroups.JChannel;
import org.jgroups.ReceiverAdapter;
//import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.conf.ClassConfigurator;
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
	protected static Log logger = LogFactory.getLog(JGroupsAbstractFacade.class); 
	// Channel object, this is part of Jgroups API
	Channel channel;
	RpcDispatcher disp;
	RspList rsp_list;
//	RequestOptions opts;
//	 RequestHandler disp;

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
//		configuratormagicmap();
		
		this.groupName = groupName;
		this.jeb = jeb;
		try {
			channel = new JChannel(props);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void configuratormagicmap() {
		try {
//			ClassConfigurator.getInstance().add(new Short("1"), org.jgroups.stack.IpAddress.class);
			ClassConfigurator.getInstance().add((short) 2, org.jgroups.protocols.Causal.class);
//			ClassConfigurator.getInstance().add((short) 3, org.jgroups.stack.IpAddress.class);
			ClassConfigurator.getInstance().add((short) 7, org.jgroups.protocols.FragHeader.class);
			ClassConfigurator.getInstance().add((short) 13, org.jgroups.protocols.PingHeader.class);
			ClassConfigurator.getInstance().add((short) 14, org.jgroups.protocols.TcpHeader.class);
			ClassConfigurator.getInstance().add((short) 19, org.jgroups.protocols.TunnelHeader.class);
			ClassConfigurator.getInstance().add((short) 20, org.jgroups.protocols.UdpHeader.class);
			ClassConfigurator.getInstance().add((short) 21, org.jgroups.protocols.pbcast.NakAckHeader.class);
//			ClassConfigurator.getInstance().add((short) 22, org.jgroups.stack.IpAddress.class);
//			ClassConfigurator.getInstance().add((short) 24, org.jgroups.stack.IpAddress.class);
//			ClassConfigurator.getInstance().add((short) 25, org.jgroups.stack.IpAddress.class);
//			ClassConfigurator.getInstance().add((short) 27, org.jgroups.stack.IpAddress.class);
		} catch (IllegalArgumentException | ChannelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	public void closeComunication(){
		if(channel==null)
			return;
		if(channel.isConnected())
			channel.disconnect();
		if(channel.isOpen())
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

	public static Log getLogger() {
		return logger;
	}

	public static void setLogger(Log logger) {
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

//	public RequestOptions getOpts() {
//		return opts;
//	}
//
//	public void setOpts(RequestOptions opts) {
//		this.opts = opts;
//	}

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
