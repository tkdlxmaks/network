package kr.ac.sungkyul.thread;

public class ThreadExample {

	public static void main(String[] args) {

		/* 스레드 생성 */
		DigitThread thread1 = new DigitThread();
		AlphabetThread thread2 = new AlphabetThread();
		DigitThread thread3 = new DigitThread();
		Thread thread4 = new Thread(new UpperCaseAlphabetRunnableImpl()); // 매개변수에 클래스를 생성
		/* 스레드 실행 */
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		System.out.println(Thread.activeCount());

	}

}
