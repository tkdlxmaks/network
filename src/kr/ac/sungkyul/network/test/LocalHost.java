package kr.ac.sungkyul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class LocalHost {

	public static void main(String[] args) {
		/**
		 * 로컬 호스트의 주소를 빼오는 방법
		 */
		try {
			/* 자신의 LocalHost를 얻어와서 inetAdress라는 객체로 담아놓고 리턴 */
			InetAddress inetAdress = InetAddress.getLocalHost();

			String hostName = inetAdress.getHostName();
			String hostAddress = inetAdress.getHostAddress();
			System.out.println(hostName);
			System.out.println(hostAddress);

			/* byte하나당 1개씩 저장 */
			byte[] addresses = inetAdress.getAddress();

			for (int i = 0; i < addresses.length; i++) {
				System.out.print(addresses[i] & 0x000000ff); // 중요중요!!!!!!!!!!
				if (i < addresses.length - 1) {
					System.out.print(".");
					/*
					 * 음수가 나오는 이유 byte는 -128~127까지이며 넘을시 음수가 바뀌며 돌아감 바꾸려면 ??
					 * integer로 확장 ? (Casting) 그래도 음수 출현
					 */
				}
			}

			/* LocalHost가 존재하지 않는경우 */
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
