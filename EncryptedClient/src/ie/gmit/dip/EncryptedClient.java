package ie.gmit.dip;

import java.io.IOException;

public class EncryptedClient {

	public static void main(String[] args) throws IOException {

		Thread thread1 = new ClientThreadSendEncrypt();
		Thread thread2 = new ClientThreadReceiveDecrypt();
		thread1.start();
		thread2.start();
		

	}

}