// $Id: CausalDemo.java,v 1.6 2005/05/30 16:14:40 belaban Exp $
package co.edu.icesi.ketal.test.causal;

import org.jgroups.*;
import org.jgroups.logging.Log;
import org.jgroups.logging.LogFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Vector;

import org.jgroups.protocols.TransportedVectorTime;
import co.edu.icesi.ketal.core.Event;


import java.net.URL;


/**
 * Simple causal demo where each member bcast a consecutive letter from the
 * alphabet and picks the next member to transmit the next letter. Start a
 * few instances of CausalDemo and pass a paramter "-start" to a CausalDemo
 * that initiates transmission of a letter A. All participanting members should
 * have correct alphabet. DISCARD layer has been added to simulate lost messages,
 * thus forcing delaying of delivery of a certain alphabet letter until the causally
 * prior one has been received.  Remove CAUSAL from the stack and witness how FIFO
 * alone doesn't provide this guarantee.
 *
 * @author Vladimir Blagojevic
 * 
 * 
 * Modified by Andres Barrera 02-Nov-2011
 */
public class CausalDemo implements Runnable
{
	private Channel channel;
	private final Vector<String> alphabet = new Vector<String>();
	private boolean starter = false;
	private int doneCount=0;
	private Log log=LogFactory.getLog(getClass());
   
   private final String props = "UDP(mcast_send_buf_size=32000;mcast_port=45566;ucast_recv_buf_size=64000;" +
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
   
   /*private final String props = "UDP(mcast_addr=228.8.8.8;mcast_port=45566;ip_ttl=32;" +
           "mcast_send_buf_size=150000;mcast_recv_buf_size=80000):" +
           "PING(timeout=2000;num_initial_members=5):" +
           //"DISCARD(up=0.05;excludeItself=true):" +
           "FD_SOCK:" +
           "VERIFY_SUSPECT(timeout=1500):" +
           "pbcast.NAKACK(retransmit_timeout=300,600,1200,2400,4800,9600):" +
           "UNICAST(timeout=5000):" +
           "pbcast.STABLE(desired_avg_gossip=2000):" +
           "FRAG(frag_size=4096):" +
           "pbcast.GMS(join_timeout=5000;print_local_addr=true)";*/

   /**
    * Constructor
    * @param start boolean value to indicate if this instance of the application will send the first letter
 	*/
   public CausalDemo(boolean start)
   {
      starter = start;
   }

   /**
    * Receives a character and increases its value
    * @param c String value representing a character
    * @return String representing the next character
    */
   public String getNext(String c)
   {
      char letter = c.charAt(0);
      return new String(new char[]{++letter});
   }
   
   /**
    * Shows the alphabet with the letters received
    */
   public void listAlphabet()
   {
      System.out.println(alphabet);
   }
   
   /**
    * Connects with the group and send the first letter if it's the starter
    * @param receiver CausalReceiver used to receive messages from the group
    */
   private void initialize(CausalReceiver receiver)
   {
	   Address address = null;
	   
	   try
	   {
		   channel = new JChannel(props);
		   channel.setReceiver(receiver);
		   channel.connect("CausalGroup");
		   System.out.println("View:" + channel.getView());
		   if (starter)
		   {
			   address = channel.getView().getMembers().get(0);
			   channel.send(new Message(null, null, new CausalMessage("A", address.toString())));
		   }

	   }
	   catch (Exception e)
	   {
		   e.printStackTrace();
		   System.out.println("Could not connect to channel");
		   return;                                                                                 
	   }
	   
   }
   
   /**
    * Shows the final alphabet when the application ends
 	*/
   private void shutDownHook()
   {
	   try
	   {
		   Runtime.getRuntime().addShutdownHook(
				   new Thread("Shutdown cleanup thread")
				   {
					   public void run()
					   {
						   System.out.println("Final Alphabet");
						   listAlphabet();
						   channel.disconnect();
						   channel.close();
					   }
				   }
				   );
	   }
	   catch (Exception e)
	   {
		   System.out.println("Exception while shutting down" + e);
	   }
   }
   
   /**
    * Waits for messages sent by others nodes and responses if necessary
    * @param receiver CausalReceiver used to receive messages from the group
    */
   private void listenMessages(CausalReceiver receiver)
   {
	   
	   Message msg;
	   Random random = new Random();
	   CausalMessage cm;
	   
	   while (true)
	   {
		   try
		   {
			   
			   cm = null;
			   msg = receiver.getMsg();
			   
			   if (msg != null)
			   {
				   cm = (CausalMessage) msg.getObject();
				   System.out.println("TVT: "+ cm.getTransportedVectorTime());

				   List<Address> members = channel.getView().getMembers();
				   String receivedLetter = cm.message;

				   if("Z".equals(receivedLetter))
				   {
					   channel.send(new Message(null, null, new CausalMessage("done", null)));
				   }
				   if("done".equals(receivedLetter))
				   {
					   if(++doneCount >= members.size())
					   {
						   System.exit(0);
					   }
					   continue;
				   }

				   alphabet.add(receivedLetter);
				   listAlphabet();

				   //am I chosen to transmit next letter?
				   if (cm.member.equals(channel.getAddress().toString()))
				   {
					   int nextTarget = random.nextInt(members.size());

					   //chose someone other than yourself
					   while (nextTarget == members.indexOf(channel.getAddress()))
					   {
						   nextTarget = random.nextInt(members.size());
					   }
					   Address next = (Address) members.get(nextTarget);
					   String nextChar = getNext(receivedLetter);
					 			   
					   
					   if (nextChar.compareTo("Z") < 1)
					   {
						   System.out.println("Sending " + nextChar);
						   channel.send(new Message(null, null, new CausalMessage(nextChar, next.toString())));
					   }
				   }
			   }

		   }
		   catch (Exception e)
		   {
			   log.error(e.getLocalizedMessage());
		   }
	   }
	   
   }

   /* (non-Javadoc)
    * @see java.lang.Runnable#run()
    */
   @Override
   public void run()
   {
	   CausalReceiver receiver = new CausalReceiver();
	   
	   initialize(receiver);
	   shutDownHook();
	   listenMessages(receiver);
	   
   }


   /**
    * @param args
 	*/
   public static void main(String args[])
   {
	   CausalDemo test = null;
	   boolean start = false;

	   for(int i=0; i < args.length; i++)
	   {
		   if("-help".equals(args[i]))
		   {
			   System.out.println("CausalDemo [-help] [-start]");
			   return;
		   }
		   if("-start".equals(args[i]))
		   {
			   start=true;
			   continue;
		   }
	   }

	     test = new CausalDemo(start);
		   try
		   {
			   new Thread(test).start();
		   }
		   catch (Exception e)
		   {
			   System.err.println(e);
		   }
	  
   }

}

/**
 * Class to encapsulate the message info
 * @author Andres Barrera
 */
class CausalMessage implements Serializable, co.edu.icesi.ketal.core.Event
{

	private static final long serialVersionUID = 780718666884109552L;
	public final String message;
	public final String member;

	public CausalMessage(String message, String member)
	{
		this.message = message;
		this.member = member;
	}

	public String toString()
	{
		return "CausalMessage[" + message + '=' + message + "member=" + member + ']';
	}
	
	@Override
	public boolean equals(Event e) {
		//No need this method
		return false;
	}

	@Override
	public URL getLocalization() {
		//No need this method
		return null;
	}

	@Override
	public boolean setLocalization(URL url) {
		//No need this method
		return false;
	}

	@Override
	public List<URL> getTargetLocalization() {
		//No need this method
		return null;
	}

	@Override
	public boolean setTargetLocalization(List<URL> url) {
		//No need this method
		return false;
	}

	private TransportedVectorTime tvt;
	@Override
	public TransportedVectorTime getTransportedVectorTime() {
		return tvt;
	}

	@Override
	public boolean setTransportedVectorTime(TransportedVectorTime tvt) {
		if(this.tvt==null)
		{
			this.tvt=tvt;
		}		
		this.tvt=tvt;
		return true;
	}

}

/**
 * ReceiverAdapter used to receive messages from the group and put it in a queue
 * @author Andres Barrera
 */
class CausalReceiver extends ReceiverAdapter
{

	private LinkedList<Message> msgs = new LinkedList<Message>();
	
	@Override
	public void receive(Message msg)
	{
		msgs.addLast(msg);
	}

	public Message getMsg()
	{
		try
		{
			return msgs.removeFirst();
		}
		catch (NoSuchElementException e)
		{
			return null;
		}
	}
	
	public LinkedList<Message> getMessages(){
		return msgs;
	}
	
}