package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Client가 다시 메시지를 받는 Thread
 * 
 * @author WonHo
 */
public class ChatClientReceiveThread extends Thread {

	private BufferedReader bufferedReader;

	public ChatClientReceiveThread(BufferedReader bufferedReader) {
		this.bufferedReader = bufferedReader;
	}

	@Override
	public void run() {
		try {

			while (true) {

				String receive = bufferedReader.readLine();
				
				if (receive == null) { // 서버가 연결은 끊음
					break;
				}
				System.out.println(receive);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
