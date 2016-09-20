package core;

public class HelloWorld {
	
	public String helloMethod(){
		return "Hello from " + this.getClass().getName();
	}
	
	public String worldMethod(){
		return "HelloWorld from " +  this.getClass().getName();
	}
}
