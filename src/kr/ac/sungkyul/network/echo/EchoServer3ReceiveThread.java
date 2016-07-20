package kr.ac.sungkyul.network.echo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class EchoServer3ReceiveThread extends Thread {

	private Socket socket;

	public EchoServer3ReceiveThread(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {

		try {
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress(); // casting

			// connect 성공
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			System.out.println("connect success:");
			int remotePort = remoteAddress.getPort();
			consoleLog("연결 from " + remoteHostAddress + " " + remotePort);
			BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "utf-8"));
			PrintWriter pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), "utf-8"), true); // auto
			// fulsh()

			while (true) {

				String data = br.readLine();

				// Client 님께서 disconnected
				if (data == null) {
					consoleLog("Client 님께서 disconnected");
					return;
				}

				// 출력
				// System.out.println("[echo server] " + data);

				// 출력
				consoleLog("received : " + data);
				// 데이터 쓰기 (echo)
				pw.println(data);
			}

		} catch (SocketException e) {
			consoleLog("[echo server] 비정상적 종료" + e);
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

	/**
	 * log로 찍는거
	 * 
	 * @param message
	 */
	private void consoleLog(String message) {
		System.out.println("[echo server thread#" + getId() + "]" + message);
	}
}
