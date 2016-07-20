package kr.ac.sungkyul.network.test;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient {
	private static final String SERVER_IP = "172.16.106.119";
	private static final int SERVER_PORT = 2020;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner =null;
		try {
			// 소켓 생성
			socket = new Socket();

			// 서버의 IP주소, port번호
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);

			System.out.println("wait...");
			// connect
			socket.connect(inetSocketAddress);
			System.out.println("coneect success");
			System.out.println("종료시 q 입력");
			
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();
			
			while (true) {

				
				System.out.print(">>");
				scanner = new Scanner(System.in);
				String data = scanner.nextLine();
				// os
				os.write(data.getBytes("utf-8"));

				// is
				byte[] buffer = new byte[256];
				int readBytes = is.read(buffer);

				if (readBytes <= -1) { // 서버가 연결은 끊음
					System.out.println("[client] close by server");
					return;
				}
				
				data = new String(buffer, 0, readBytes, "utf-8");
				System.out.println("<<" + data);

				if (data.equals("q")) {
					// 자원 정리
					socket.close();
				}
			}
		} catch (SocketException e) {
			System.out.println("client exit");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (socket != null && socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}

	}

}
