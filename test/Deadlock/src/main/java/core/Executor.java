package core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Executor {

	public static void main(String[] args) {
		TreeCache tree = new TreeCache();
    	System.setProperty("java.net.preferIPv4Stack" , "true");

		
		try {
			BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
			
			String r = "";
			boolean bool = false;
			while(!bool){
				System.out.println("Please write an event");
				r = bf.readLine();
				if(r.equals("stop")){
					bool = true;
				}else if(r.equals("prepare")){
					tree.prepare();
				}else if(r.equals("commit")){
					tree.commit();
				}
			}
			bf.close();
			
			System.out.println("termina");
			
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
