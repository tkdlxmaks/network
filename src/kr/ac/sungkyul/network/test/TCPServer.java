package kr.ac.sungkyul.network.test;

import java.io.*;
import java.net.*;

public class TCPServer {
	private final static int SERVER_PORT = 2001;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		try {

			/* 1. 서버소켓 생성 */
			serverSocket = new ServerSocket();

			/* 2. 바인딩 */
			/**
			 * inetSocket Class라 InetSocketAddress 를 사용 매개변수의 "220.123.___" 이렇게
			 * 작성시 다른 사용자가 셋팅해야하는 번거로움이 있다. 그렇기에 자신 host를 받을 수 있는 매개변수 사용
			 */
			InetAddress inetAddress = InetAddress.getLocalHost();
			String serverAddress = inetAddress.getHostAddress();
			InetSocketAddress inetSocketAddress = new InetSocketAddress(serverAddress, SERVER_PORT); // (hostName,포트번호)

			
			serverSocket.bind(inetSocketAddress);
			System.out.println("[server] binding = " + serverAddress + ":" + SERVER_PORT);

			/* 3. accpet (Client로부터 요청대기) */
			Socket socket = serverSocket.accept(); // blocking 지점

			// Client의 SockㅇetAddress를 받아옴 - 연결한 ip Address와 Port번호를 얻어옴
			InetSocketAddress remoteAddress = (InetSocketAddress) socket.getRemoteSocketAddress();
			String remoteHostAddress = remoteAddress.getAddress().getHostAddress();
			int remoteHostPort = remoteAddress.getPort();
			
			/* 4. 연결성공 */
			System.out.println("[server] 연결성공 from = " + remoteHostAddress + ":" + remoteHostPort);
			
			try {
			/* 5. IOStream */
			InputStream is = socket.getInputStream();
			OutputStream os = socket.getOutputStream();

			/* 6. 데이터 읽기 - 바이트 단위 , is,os 사용 */
			byte[] buffer = new byte[256]; // blcoked
			int readBytes = is.read(buffer); // 읽은수를 return 자료형 int
			
			/* 정상종료 */
			if (readBytes <= -1) { // 클라이언트가 연결은 끊었다.
				System.out.println("[server] closed by client");
				return;
			}	
			String data = new String(buffer , 0 ,readBytes, "utf-8"); // 이 내용을 InputStreamReader를 이용하는 방법_0719때 할 것
			System.out.print("[server] received : " + data);
			
			/* 7. 데이터 쓰기*/
			os.write(data.getBytes("utf-8"));
			} catch (SocketException e) {
				System.out.println("[server] 비정상적으로 client disconnect");
			} catch (IOException ee) {
				ee.printStackTrace();
			} finally {
				/* 8. 소켓닫기 */
				if(socket!=null && socket.isClosed() == false) {
					socket.close();
				} 
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (serverSocket != null && serverSocket.isClosed() == false) {
				try {
					//서버 소켓 닫기
					serverSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
