package kr.ac.sungkyul.thread;

public class UpperCaseAlphabet {
	
	public void print() {
		for(int i='A'; i<='Z'; i++) {
			try {
			System.out.print((char)i);
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
