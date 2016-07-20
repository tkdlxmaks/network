package kr.ac.sungkyul.network.test;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

public class NSLookup {

	public static void main(String[] args) {

		Scanner scanner = new Scanner(System.in);
		String host = null;
		while (true) {
			try {
				System.out.print(">>");
				host = scanner.nextLine();
				InetAddress[] inetAddresses = InetAddress.getAllByName(host);

				for (InetAddress inetAddress : inetAddresses) {
					System.out.println(inetAddress.getHostAddress());
				}

			} catch (UnknownHostException e) {
				if ("quit".equals(host)) {
					System.out.println("종료합니다.");
					break;
				}
				System.out.println("잘못된 경로입니다. 다시입력해주세요");
			}

		}

	}
}
