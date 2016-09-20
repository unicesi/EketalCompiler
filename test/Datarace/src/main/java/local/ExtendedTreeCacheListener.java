package local;

public class ExtendedTreeCacheListener {
	
	public void peek(){
		System.out.println("Method peek from ExtendedTreeCacheListener");
	}
	
	public void nodeEvict(){
		System.out.println("Method nodeEvict from ExtendedTreeCacheListener");
	}
	
}
