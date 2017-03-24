package co.edu.icesi.ketal.distribution.transports.jgroups;

/**
 * This class is a singleton class that serves as central point
 * for Distribution configuration parameters.
 * This version is inherited form AWED� first implementation and some of
 * the available parameters are not used.
 * 
 * The communication stack is a configuration string that is passed to JGroups to configure the
 * communication stack used to send and receive messages.
 * 
 * The class also supports parameters to address causality, customized sockets, and RMI exception port.
 * 
 * A clear improvement for the class is to get parameters from a XML file instead of have them burned in the code. 
 * However, considering this is going to be used in programming environments, 
 *  using system properties for configuration seems more intuitive.
 * 
 * @author Luis Daniel Benavides Navarro
 * */
public class DistributionProperties {
	static private DistributionProperties _DistributedOptionsInstance = new DistributionProperties();

	private boolean useCustomizedSockets;
	private int RMIExceptionPort;
	private boolean usingCausalSupport;
	
	// Default logger
	final static org.apache.logging.log4j.Logger logger = org.apache.logging.log4j.LogManager
			.getLogger(DistributionProperties.class);
	
	private DistributionProperties() {
		initializeParameters();
	}
	
	public static DistributionProperties getInstance(){
		return _DistributedOptionsInstance;
	}
	
	public String getStack(String optionsSetName){
		String stack = "";
		String addr = System.getProperty("bind.addr");
		String port = System.getProperty("bind.port");
		String protocol = System.getProperty("protocol");
		
		// Option to set causality support inside awed messages.
		// This option activates causal tagging of AWED messages
		// and vector clock increase by AWED messages. It is used
		// to detect causal relations between AWED messages.
		String causality = System.getProperty("causality");
		
		// This option is used to monitor causality over a non causal distributed
		// application. It has to be used with an adpator, e.g an RMI causal adaptor
		// see the implementation of customized sockets'clasees: JascoInputStream and JascoOutputStream
		String causalMonitoring = System.getProperty("causalmonitor");
		
		
		if(addr == null){
			addr ="";
		}else{
			addr =";" + "bind_addr=" + addr;
		}
		if(port == null){
			port ="";
		}else{
			port =";" + "bind_port=" + port;
		}
		
//		System.out.println("Options prototcol:" + protocol);
		logger.debug("Options prototcol:" + protocol);
		if((protocol == null) || (protocol.equals("UDP"))){
			stack ="UDP(mcast_send_buf_size=32000;mcast_port=45566;ucast_recv_buf_size=64000;" +
				   "mcast_addr=228.8.8.8;loopback=true;mcast_recv_buf_size=64000;max_bundle_size=60000;" +
				   "max_bundle_timeout=30;use_incoming_packet_handler=false;use_outgoing_packet_handler=false;" +
				   "ucast_send_buf_size=32000;ip_ttl=32;enable_bundling=false" +
		    addr + 
		    port + "):" +
		    "PING(timeout=2000;num_initial_members=3):" +
		    "MERGE2(min_interval=10000;max_interval=20000):" +
		    "FD(timeout=2000;max_tries=4):" +
		    "VERIFY_SUSPECT(timeout=1500):" +
		    "pbcast.NAKACK(use_mcast_xmit=false;gc_lag=50;retransmit_timeout=600,1200,2400,4800):" +
		    "UNICAST(timeout=1200,2400,3600):" +
		    "pbcast.STABLE(stability_delay=1000;desired_avg_gossip=20000;max_bytes=0):" +
		    "FRAG(frag_size=8192):" +
		    			               "print_local_addr=false;down_thread=true;up_thread=true):Causal(causal_order_prot_interest=false)"; //CausalOrder has a parameter to indicates if the protocol organized or not the messages.

		    //"AWEDLOCALCLOCKMANAGEMENT";
		    //"CAUSALTAGS";
		    //+
		    //"pbcast.STATE_TRANSFER(up_thread=true;down_thread=true)";
		}
		else if(protocol.equals("TCP")){
			 stack = "TCP(start_port=7800):" +

				   "mcast_addr=228.8.8.8;loopback=true;mcast_recv_buf_size=64000;max_bundle_size=60000;" +
				   "max_bundle_timeout=30;use_incoming_packet_handler=false;use_outgoing_packet_handler=false;" +
				   "ucast_send_buf_size=32000;ip_ttl=32;enable_bundling=false" +
		    addr + 
		    port + "):" +
		    "PING(timeout=2000;num_initial_members=3):" +
		    "MERGE2(min_interval=10000;max_interval=20000):" +
		    "FD(timeout=2000;max_tries=4):" +
		    "VERIFY_SUSPECT(timeout=1500):" +
		    "pbcast.NAKACK(use_mcast_xmit=false;gc_lag=50;retransmit_timeout=600,1200,2400,4800):" +
		    "UNICAST(timeout=1200,2400,3600):" +
		    "pbcast.STABLE(stability_delay=1000;desired_avg_gossip=20000;max_bytes=0):" +
		    "FRAG(frag_size=8192):" +
		    			               "print_local_addr=false;down_thread=true;up_thread=true):Causal(causal_order_prot_interest=false)";
		}
		
		String stackTail = ""; //":pbcast.STATE_TRANSFER(up_thread=true;down_thread=true)";
		
		//Note that causality has precedence over causal monitoring
		//TODO test TCP with causality
		if("true".equals(causality)) {
			stackTail = ":AWEDLOCALCLOCKMANAGEMENT";
			usingCausalSupport = true;
			}
		else if("true".equals(causalMonitoring)){
			stackTail =  ":CAUSALTAGS";
			usingCausalSupport = true;
			
			// This option does not activate customized sockets by default. 
			// When invoking the program the user should be careful to activate them.
		}
		stack = stack + stackTail;
		System.out.println("the stack: " + stack );
		return stack;
	}
	
	public String getGroupName(String optionsSetName){
		String addr = System.getProperty("awed.group.name");
		return addr + optionsSetName;
	}

	public boolean getUseCustomizedSockets() {
		return useCustomizedSockets;
	}
	
	public int getRMIExceptionPort() {
		return RMIExceptionPort;
	}
	
	private void initializeParameters() {
		initializeUseCustomizedSockets();
		initializeRMIRegistryPort();
	}
	
	public void initializeUseCustomizedSockets() {
		String s = System.getProperty("awed.distribution.CustomizedSockets");
		logger.debug("awed.distribution.CustomizedSockets: "+s);
//		System.out.println("awed.distribution.CustomizedSockets: "+s);
		if ("true".equals(s) )
			useCustomizedSockets = true;
		else
			useCustomizedSockets = false;
		logger.debug("useCustomizedSockets set to: "+useCustomizedSockets);
		//System.out.println("useCustomizedSockets set to: "+useCustomizedSockets);
	}
	
	public void initializeRMIRegistryPort() {
		String portString = System.getProperty("awed.distribution.CustomizedSocketsExceptionPort");
		try {
			RMIExceptionPort = Integer.parseInt(portString);
		} catch (Exception e) {
			RMIExceptionPort = -1;
		}
	}
	
	public boolean isUsingCausalSupport() {
		return usingCausalSupport;
	}
	
}
