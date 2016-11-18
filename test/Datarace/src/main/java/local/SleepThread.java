package local;

public class SleepThread extends Thread {
	private int wait;
	
	public SleepThread() {
		wait = 3000;
	}
	
	public SleepThread(int sleep) {
		wait = sleep;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			sleep(wait);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
