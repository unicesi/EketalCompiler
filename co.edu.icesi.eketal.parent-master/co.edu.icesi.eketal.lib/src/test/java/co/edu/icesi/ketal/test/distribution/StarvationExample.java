package co.edu.icesi.ketal.test.distribution;

// starvation sample class, 
// illustrates thread starvation
// (especially on a single cpu machine)
public class StarvationExample implements Runnable {
	private final Object resource_;
	private final String message_;
	private final boolean fair_;

	// program entry point
	public static void main(String[] args) {
		boolean fair = false;
		if (args != null && args.length >= 1 && args[0].equals("fair")) {
			fair = true;
		}

		// get the number of available cpus; do twice as much threads
		final int cpus = Runtime.getRuntime().availableProcessors();
		System.out.println("" + cpus + " available cpus found");
		final int runners = cpus * 2;
		System.out.println("starting " + runners + " runners");

		final Object resource = new Object();

		// create sample runners and start them
		for (int i = 1; i <= runners; i++) {
			(new Thread(
					new StarvationExample(resource, String.valueOf(i), fair)))
					.start();
		}

		// suspend main thread
		synchronized (StarvationExample.class) {
			try {
				StarvationExample.class.wait();
			} catch (InterruptedException ignored) {
			}
		}
	}

	public StarvationExample(Object resource, String message, boolean fair) {
		resource_ = resource;
		message_ = message;
		fair_ = fair;
	}

	public void run() {
		synchronized (this) {
			for (;;) {
				synchronized (resource_) {
					print(message_);

					// some delay;
					// if we keep synchronized on the contended resource,
					// scheduling isn't fair,
					// (possibly leading to thread starvation)
					try {
						(fair_ ? resource_ : this).wait(100);
					} catch (InterruptedException ignored) {
					}
				}
			}
		}
	}

	private static void print(String s) {
		synchronized (System.out) {
			System.out.print(s);
			System.out.flush();
		}
	}
}

/*
 * 
 * "Thread-3" prio=1 tid=0x080db6c8 nid=0x22de waiting for monitor entry
 * [0xa81e4000..0xa81e4150] at starvation.run(starvation.java:55) - waiting to
 * lock <0xa89fc7f0> (a java.lang.Object) - locked <0xa89fcdb0> (a starvation)
 * at java.lang.Thread.run(Thread.java:595)
 * 
 * "Thread-2" prio=1 tid=0x080da780 nid=0x22dd waiting for monitor entry
 * [0xa8264000..0xa82650d0] at starvation.run(starvation.java:55) - waiting to
 * lock <0xa89fc7f0> (a java.lang.Object) - locked <0xa89fcc48> (a starvation)
 * at java.lang.Thread.run(Thread.java:595)
 * 
 * "Thread-1" prio=1 tid=0x080d9838 nid=0x22dc waiting for monitor entry
 * [0xa82e5000..0xa82e6050] at starvation.run(starvation.java:55) - waiting to
 * lock <0xa89fc7f0> (a java.lang.Object) - locked <0xa89fcae0> (a starvation)
 * at java.lang.Thread.run(Thread.java:595)
 * 
 * "Thread-0" prio=1 tid=0x080d9100 nid=0x22db in Object.wait()
 * [0xa8366000..0xa8366fd0] at java.lang.Object.wait(Native Method) - waiting on
 * <0xa89fc978> (a starvation) at starvation.run(starvation.java:62) - locked
 * <0xa89fc7f0> (a java.lang.Object) - locked <0xa89fc978> (a starvation) at
 * java.lang.Thread.run(Thread.java:595)
 */

