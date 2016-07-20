package kr.ac.sungkyul.network.chat;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.Port;

public class ChatServer {
	// 포트번호
	private static final int SERVER_PORT = 8080;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		List<PrintWriter> listWriter = new ArrayList<PrintWriter>();

		try {
			// 소켓 생성
			serverSocket = new ServerSocket();

			// 자신 IP주소(server) 가져오기
			String hostAddress = InetAddress.getLocalHost().getHostAddress();
			// IP주소 , 포트번호
			InetSocketAddress inetSocketAddress = new InetSocketAddress(hostAddress, SERVER_PORT);
			// binding
			serverSocket.bind(inetSocketAddress);

			log("wait..\r\n" + hostAddress + ":" + SERVER_PORT);

			while (true) {

				// 연결 대기
				Socket socket = serverSocket.accept();

				new ChatServerThread(socket, listWriter).start();
			}

		} catch (IOException e) {
			log("error :" + e);
		} finally {
			if (serverSocket != null && serverSocket.isClosed() == false) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					log("종료");
				}
			}
		}

	}

	public static void log(String message) {
		System.out.println("[chat-server] " + message);
	}

}
