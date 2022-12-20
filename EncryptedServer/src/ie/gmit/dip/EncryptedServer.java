package ie.gmit.dip;

import java.io.IOException;

public class EncryptedServer {

	public static void main(String[] args) throws IOException {

		Thread thread1 = new ServerThreadSendEncrypt();
		Thread thread2 = new ServerThreadReceiveDecrypt();
		thread1.start();
		thread2.start();

	}

}
