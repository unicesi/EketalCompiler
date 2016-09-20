package test;

import static org.junit.Assert.*;

import org.junit.Test;

import local.ExtendedTreeCacheListener;
import local.Transaction;
import remote.CacheLoader;

public class TestDatarace {

	@Test
	public void test() {
		
		CacheLoader loader = new CacheLoader();
		Transaction transaction = new Transaction();
		ExtendedTreeCacheListener listener = new ExtendedTreeCacheListener();
		
		loader.get();
		transaction.begin();
		listener.nodeEvict();
		transaction.commit();
		
		System.out.println("-------------------||||||||||||-------------------");
		
		loader.get();
		transaction.begin();
		listener.peek();
		listener.nodeEvict();
		
	}

}
