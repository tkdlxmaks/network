package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer2 {

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

			// 서버소켓 -> 일반 소켓
			Socket socket = serverSocket.accept(); // 서버소켓의 받은정보를 소켓에다 이동
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress(); // casting

			// connect 성공
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			System.out.println("connect success:" + remoteHostAddress + " " + SERVER_PORT);

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true); // auto
																										// fulsh()

			while (true) {

				String data = br.readLine();

				// Client 님께서 disconnected
				if (data == null) {
					System.out.println("Client 님께서 disconnected");
					return;
				}

				// 출력
				System.out.println("Client에게 받은 내용 " + data);

				// 데이터 쓰기 (echo)
				pw.println(data);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null && serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
