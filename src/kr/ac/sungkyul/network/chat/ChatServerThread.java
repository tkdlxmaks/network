package kr.ac.sungkyul.network.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ChatServerThread extends Thread {
	private String nickname;
	private Socket socket;
	private List<PrintWriter> listWriters;

	// 소켓 연결
	public ChatServerThread(Socket socket, List<PrintWriter> listWriters) {
		this.socket = socket;
		this.listWriters = listWriters;
	}

	@Override
	public void run() {
		PrintWriter printWriter = null;
		BufferedReader bufferedReader = null;

		try {
			// 1. Remote Host Information
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();

			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			int remotePort = remoteAddress.getPort();
			ChatServer.log(remoteHostAddress + ":" + remotePort + " is connected");

			// 2. 스트림 얻기
			bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8));
			printWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8),
					true);

			// 3. 요청 처리
			while (true) {
				String request = bufferedReader.readLine();
				if (request == null) {
					ChatServer.log("Client by connect is closed");
					doQuit(printWriter);
					break;
				}
				// 4. 프로토콜 분석
				String[] tokens = request.split(":"); // join:nickname

				if ("join".equals(tokens[0])) {
					doJoin(tokens[1], printWriter);

				} else if ("message".equals(tokens[0])) {
					doMessage(tokens[1]);

				} else if ("quit".equals(tokens[0])) {
					doQuit(printWriter);
					break;

				} else {
					log("에러:알수없는요청(" + tokens[0] + ")");
				}
			}

		} catch (SocketException se) {
			log("종료하였습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				// 자원정리
				bufferedReader.close();
				printWriter.close();
				if (socket.isClosed() == false) {
					socket.close();
				}
			} catch (IOException ex) {
				ChatServer.log("error:" + ex);
			}
		}
	}

	private void doQuit(PrintWriter printWriter) {

		removePrintWriter(printWriter);
		String data = nickname + "님이 퇴장하였습니다.";
		broadcast(data);
	}

	private void removePrintWriter(PrintWriter printWriter) {
		synchronized (listWriters) {
			listWriters.remove(printWriter);
		}
	}

	private void doMessage(String message) {
		String data = nickname + ":" + message;
		broadcast(data);
	}

	private void doJoin(String nickName, PrintWriter printWriter) {
		this.nickname = nickName;
		String data = nickname + "님이 참여하였습니다.";

		System.out.println(data);
		broadcast(data);

		/* writer pool에 저장 */
		addWriter(printWriter);

		// ack
		printWriter.println("join:ok");
		printWriter.flush();
	}

	private void addWriter(PrintWriter printWriter) {

		synchronized (listWriters) {
			listWriters.add(printWriter);
		}
	}

	private void broadcast(String data) {
		synchronized (listWriters) {

			int count = listWriters.size();
			for (int i = 0; i < count; i++) {
				PrintWriter printWriter = (PrintWriter) listWriters.get(i);
				printWriter.println(data);
				printWriter.flush();
			}
		}
	}

	public void log(String message) {
		System.out.println("[" + nickname + "]" + message);
	}
}
