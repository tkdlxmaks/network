package kr.ac.sungkyul.network.test;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;

public class TCPClient {
	private static String SERVER_IP = "172.16.106.119";
	private static int SERVER_PORT = 2001;

	public static void main(String[] args) {

		Socket socket = null;

		try {
			/* 1. 소켓 생성 */
			socket = new Socket();

			/* 2. 서버 연결 */
			InetSocketAddress serverSocketAddress = new InetSocketAddress(SERVER_IP, SERVER_PORT);
			socket.connect(serverSocketAddress);

			/* 3. IOStream 받아오기 */
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			/* 4. 데이터 쓰기 */
			String data = "Hello World\n";
			os.write(data.getBytes("UTF-8"));

			/* 5. 데이터 읽기 */
			byte[] buffer = new byte[256];
			int readBytes = is.read(buffer);
			if (readBytes <= -1) { // 서버가 연결은 끊음
				System.out.println("[client] close by server");
				return;
			}

			data = new String(buffer, 0, readBytes, "UTF-8");
			System.out.println("[client] received:" + data);
		} catch (SocketException e) {
			System.out.println("[client] 비정상적으로 server disconnect");
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			/* 8. 소켓닫기 */
			try {
				if (socket != null && socket.isClosed() == false) {
					socket.close();
				}
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
}