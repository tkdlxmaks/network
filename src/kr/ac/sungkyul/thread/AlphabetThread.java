package kr.ac.sungkyul.thread;

public class AlphabetThread extends Thread {

	@Override
	public void run() {

		// thread 1
		for (int i = 'a'; i <= 'z'; i++) {
			try {
				System.out.print((char) i);
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
