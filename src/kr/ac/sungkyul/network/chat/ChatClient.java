package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class ChatClient {
	// 서버의 IP주소, port번호
	private static final String SERVER_IP = "172.16.106.119";
	private static final int SERVER_PORT = 8080;

	public static void main(String[] args) {

		Scanner scanner = null;

		Socket socket = null;
		BufferedReader bufferedReader = null;
		PrintWriter printWriter = null;
		try {

			// 1. 키보드 연결
			scanner = new Scanner(System.in);

			// 2. socket 생성
			socket = new Socket();

			// 3. 연결
			InetSocketAddress inetSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(inetSocketAddress);

			// 4. reader/writer 생성
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true); // auto

			// 5. join 프로토콜
			System.out.print("닉네임>>");
			String nickname = scanner.nextLine();

			printWriter.println("join:" + nickname);

			// auto flush 를 true로 해놔서 지장없을듯
			printWriter.flush();

			bufferedReader.readLine();

			// 6. ChatClientReceiveThread 시작
			new ChatClientReceiveThread(bufferedReader).start();

			while (true) {
				System.out.print(nickname + ":");
				// client가 쓴 내용
				String input = scanner.nextLine();

				if ("quit".equals(input) == true) {
					// 8. quit 프로토콜 처리
					printWriter.println("quit");
					printWriter.flush();
					break;
				} else {
					// 9. 메시지처리
					printWriter.println("message:" + input);
					printWriter.flush();
				}
			}
		} catch (SocketException se) {
			System.out.println("[client] 비정상적 종료");
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (scanner != null) {
					scanner.close();
				}
				if (bufferedReader != null) {
					bufferedReader.close();
				}
				if (printWriter != null) {
					printWriter.close();
				}
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void log(String log) {
		System.out.println("[chat-client] " + log);
	}
}
