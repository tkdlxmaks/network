package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.Scanner;

public class EchoClient2 {
	private static final String SERVER_IP = "172.16.106.119";
	private static final int SERVER_PORT = 2020;

	public static void main(String[] args) {
		Socket socket = null;
		Scanner scanner = null;
		try {
			// 소켓 생성
			socket = new Socket();

			// 서버의 IP주소, port번호
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);

			System.out.println("wait...");
			// connect
			socket.connect(inetSocketAddress);
			System.out.println("conect success");
			System.out.println("종료시 q 입력");

			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true); // auto
																												// fulsh()

			while (true) {

				System.out.print(">>");
				scanner = new Scanner(System.in);
				String message = scanner.nextLine();

				// 메시지 보내기
				pw.println(message);

				// 메시지 다시 받기
				String messageEcho = br.readLine();

				if (messageEcho == null) { // 서버가 연결은 끊음
					System.out.println("[client] close by server");
					return;
				}
				System.out.println("<<" + messageEcho);

				if (message.equals("q")) {
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
