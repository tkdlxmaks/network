package kr.ac.sungkyul.network.echo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 
 * @author WonHo
 */
public class EchoServer3 {

	private static final int SERVER_PORT = 2020;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {

			// 서버 소켓 생성
			serverSocket = new ServerSocket();

			String localHostAddress = InetAddress.getLocalHost().getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(localHostAddress, SERVER_PORT);
			// 바인드
			serverSocket.bind(inetSocketAddress);
			System.out.println("[bind]" + localHostAddress);
			System.out.println("wait...");

			while (true) {
				// 연결 요청 대기
				Socket socket = serverSocket.accept(); // 서버소켓의 받은정보를 소켓에다 이동
				EchoServer3ReceiveThread thread = new EchoServer3ReceiveThread(socket);
				thread.start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
