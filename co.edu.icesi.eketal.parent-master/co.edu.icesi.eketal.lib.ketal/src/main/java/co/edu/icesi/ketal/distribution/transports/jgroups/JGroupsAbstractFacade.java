package co.edu.icesi.ketal.distribution.transports.jgroups;

import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jgroups.Address;
import org.jgroups.Channel;
import org.jgroups.JChannel;
import org.jgroups.PhysicalAddress;
import org.jgroups.ReceiverAdapter;
import org.jgroups.blocks.RequestOptions;
import org.jgroups.blocks.RpcDispatcher;
import org.jgroups.stack.IpAddress;
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
	protected static final Log logger = LogFactory
			.getLog(JGroupsAbstractFacade.class);

	// Channel object, this is part of Jgroups API
	Channel channel;
	RpcDispatcher disp;
	RspList rsp_list;
	RequestOptions opts;
	protected URL address;
	// RequestHandler disp;
	
	/*
	
	private String props ="TCP(bind_port=7800;"+
         "recv_buf_size=${tcp.recv_buf_size:5M};"+
         "send_buf_size=${tcp.send_buf_size:5M};"+
         "max_bundle_size=64K;"+
         "max_bundle_timeout=30;"+
         "use_send_queues=true;"+
         "sock_conn_timeout=300;"+

         "timer_type=new3;"+
         "timer.min_threads=4;"+
         "timer.max_threads=10;"+
         "timer.keep_alive_time=3000;"+
         "timer.queue_max_size=500;"+
         
         "thread_pool.enabled=true;"+
         "thread_pool.min_threads=2;"+
         "thread_pool.max_threads=8;"+
         "thread_pool.keep_alive_time=5000;"+
         "thread_pool.queue_enabled=true;"+
         "thread_pool.queue_max_size=10000;"+
         "thread_pool.rejection_policy=discard;"+

         "oob_thread_pool.enabled=true;"+
         "oob_thread_pool.min_threads=1;"+
         "oob_thread_pool.max_threads=8;"+
         "oob_thread_pool.keep_alive_time=5000;"+
         "oob_thread_pool.queue_enabled=false;"+
         "oob_thread_pool.queue_max_size=100;"+
         "oob_thread_pool.rejection_policy=discard):"+
                         
    	"TCPPING(async_discovery=true;"+
         "initial_hosts="${jgroups.tcpping.initial_hosts:supervisor1[7800],supervisor2[7800],supervisor4[7800],zookeeper[7800],supervisor6[7800]};"+
         "port_range=2):"+

	   "MERGE3(min_interval=10000;max_interval=30000):"+	   
	   "FD_SOCK():" +
	   "FD(timeout=12000;max_tries=3):"+
	   "VERIFY_SUSPECT(timeout=2000):" +
		"BARRIER():" +                           
	   "pbcast.NAKACK2(use_mcast_xmit=false;discard_delivered_msgs=true):" +
       "UNICAST3():"+

	   "pbcast.STABLE(stability_delay=1000;desired_avg_gossip=50000;max_bytes=4M):" +

	   "pbcast.GMS(join_timeout=2000;print_local_addr=true;view_bundling=true):"+
	   //"pbcast.GMS(join_timeout=5000;print_local_addr=true;view_bundling=true):Causal(causal_order_prot_interest=false)";
	   "MFC(max_credits=2M;min_threshold=0.4):"+

	   "FRAG2(frag_size=60k):" +
	   "pbcast.STATE_TRANSFER()";
	 */
	
	
	// The properties configuring the Jgroups communication stack
	// Properties are managed by class DistributionProperties
	//String props = DistributionProperties.getInstance()
			//.getStack("AspectsGroup");
	private String props = "UDP(mcast_port=${jgroups.udp.mcast_port:45588};"+
			"tos=8;"+
			"ucast_recv_buf_size=210K;"+
			"ucast_send_buf_size=210K;"+
		    "mcast_recv_buf_size=210K;"+
		    "mcast_send_buf_size=210K;"+
		    "max_bundle_size=64K;"+
		    "max_bundle_timeout=30;"+
		    "enable_diagnostics=true;"+
		    "thread_naming_pattern=cl;"+
		    "logical_addr_cache_max_size=1000;"+

		    "timer_type=new3;"+
		    "timer.min_threads=2;"+
		    "timer.max_threads=4;"+
		    "timer.keep_alive_time=3000;"+
		    "timer.queue_max_size=500;"+
			"timer.rejection_policy=abort;"+

		    "thread_pool.enabled=true;"+
		    "thread_pool.min_threads=10;"+
		    "thread_pool.max_threads=80;"+
		    "thread_pool.keep_alive_time=5000;"+
		    "thread_pool.queue_enabled=true;"+
		    "thread_pool.queue_max_size=50000;"+
		    "thread_pool.rejection_policy=discard;"+

		    "oob_thread_pool.enabled=true;"+
		    "oob_thread_pool.min_threads=5;"+
		    "oob_thread_pool.max_threads=80;"+
		    "oob_thread_pool.keep_alive_time=5000;"+
		    "oob_thread_pool.rejection_policy=discard):"+

			"PING(break_on_coord_rsp=true):" +
			"MERGE3(min_interval=10000;max_interval=30000):" +

			"FD_SOCK():" +
			"FD_ALL(timeout=12000):" +
			"VERIFY_SUSPECT(timeout=2000):" +
			"BARRIER():" +

		  	"pbcast.NAKACK2(use_mcast_xmit=true;xmit_interval=500;xmit_table_num_rows=100;xmit_table_msgs_per_row=2000;"+
			"xmit_table_max_compaction_time=35000;max_msg_batch_size=500;discard_delivered_msgs=true):" +
					   
		    "UNICAST3(xmit_interval=500;xmit_table_num_rows=100;xmit_table_msgs_per_row=2000;"+
		    "xmit_table_max_compaction_time=60000;max_msg_batch_size=500):"+

			"pbcast.STABLE(stability_delay=2000;desired_avg_gossip=50000;max_bytes=4M):" +
		    "pbcast.GMS(join_timeout=10000;print_local_addr=true;view_bundling=true,merge_timeout=7000,max_bundling_time=1000,resume_task_timeout=15000):"+

			"UFC(max_credits=4M;min_threshold=0.4):"+
			"MFC(max_credits=4M;min_threshold=0.4):"+

			"FRAG2(frag_size=60k):" +
		    "RSVP(resend_interval=2000;timeout=10000):"+
			//"pbcast.GMS(join_timeout=5000;print_local_addr=true;view_bundling=true):Causal(causal_order_prot_interest=false)";
			"pbcast.STATE_TRANSFER()";

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
			initializeAddress();
		} catch (Exception e) {
			getLogger().error(e.getMessage());
//			e.printStackTrace();
		}
	}
	
	private void initializeAddress() {
		if(address==null){
			String srcIp = "";
			Address addr = channel.getAddress();
			PhysicalAddress physicalAddr = (PhysicalAddress)channel.down(new org.jgroups.Event(org.jgroups.Event.GET_PHYSICAL_ADDRESS, addr));
			
		    if(physicalAddr instanceof IpAddress) {
		        IpAddress ipAddr = (IpAddress)physicalAddr;
		        InetAddress inetAddr = ipAddr.getIpAddress();
		        srcIp = inetAddr.getHostAddress()+":"+ipAddr.getPort();
		    }
		    
		    boolean isPhysical = isPhysical(srcIp);		    
		    
		    
		    if(srcIp=="" || isPhysical){
		    	if(addr instanceof org.jgroups.util.UUID){
		    		String string = org.jgroups.util.UUID.get(addr);
		    		try {
		    			URI uri = new URI("http://"+string.split("-")[0]+(isPhysical?":"+srcIp.split(":")[8]:""));
						address = uri.toURL();
						return;
					} catch (URISyntaxException e) {
						e.printStackTrace();
					} catch (MalformedURLException e) {
						e.printStackTrace();
					}
		    	}
		    }
		    if(srcIp==""){
		    	return;
		    }
		    URL retorno = null;
			try {
				retorno = new URL("http://"+srcIp);
			} catch (MalformedURLException e) {
				logger.error(e.getStackTrace());
				e.printStackTrace();
			}
			if(retorno==null){
				logger.error("Null en la direccion ip");
			}
			address = retorno;
		}
	}
	
	private boolean isPhysical(String srcIp) {
		return srcIp.split(":").length>2&&srcIp!="";
	}

	public URL getIpAddress(){
		if(address==null){
			initializeAddress();
		}
		return address;
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
			initializeAddress();
		} catch (Exception e) {
			getLogger().error(e.getMessage());
//			e.printStackTrace();
		}
	}

	public static Log getLogger() {
		return logger;
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
}
