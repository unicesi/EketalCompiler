package core;

public class TreeCache {
	
	public String prepare(){
		return "Preparing for commit " + this.getClass().getName();
	}
	
	public String commit(){
		return "Commit " +  this.getClass().getName();
	}
}
