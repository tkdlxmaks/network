package kr.ac.sungkyul.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServer {

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

			while (true) {
				InputStream is = socket.getInputStream();
				OutputStream os = socket.getOutputStream();

				byte[] buffer = new byte[256];
				int bytes = is.read(buffer);

				// Client 님께서 disconnected
				if (bytes == -1) {
					System.out.println("Client 님께서 disconnected");
					socket.close();
					return;
				}
				String contain = new String(buffer, 0, bytes, "utf-8");
				System.out.println("clinet에게 받은 내용 " + contain);

				os.write(contain.getBytes("utf-8"));
				os.flush(); // 비우기
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
