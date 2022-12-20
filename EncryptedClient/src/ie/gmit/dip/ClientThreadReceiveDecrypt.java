package ie.gmit.dip;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ClientThreadReceiveDecrypt extends Thread {

	/*
	 * The below method decrypts received messages by reversing their string of
	 * characters (E.G., the received encrypted message "!321tseT" becomes "Test123!").
	 */
	
	public String decryptReverse(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = s.length() - 1; i >= 0; i--) {
			sb.append(s.charAt(i));
		}

		return sb.toString();

	}

	/*
	 * The below method decrypts received messages which have been encrypted by Caesar cipher, shifting the
	 * decimal value of each character by the offset key. (E.G., with an offset of
	 * 2, 'a', which has the decimal value of 97, has been shifted to 'c', which has the decimal
	 * value of 99). This method restores the character back to its original value. (E.G., 'c' returns to 'a').
	 */
	
	public String decryptCipher(String plainText, int offset) {
		String cipher = "";
		char[] arr = plainText.toCharArray();
		for (int i = 0; i < arr.length; i++) {
			int numericalVal = (int) arr[i];
			if (Character.isUpperCase(arr[i])) {
				cipher += (char) (numericalVal + offset);
			} else if (numericalVal != 32) {
				cipher += (char) (numericalVal + offset);
			} else if (numericalVal == 32) {
				cipher += (char) (numericalVal + offset);
			}

		}
		return cipher;
	}

	/* The below method opens a socket connection on a hard coded host and port number, creates an 
	 * InputStreamReader and BufferedReader to read data from the socket, and calls the 
	 * decryption methods above to decrypt said data. This decrypted data (message from 
	 * the server) is printed to the console, until the thread is closed (by the server
	 * typing "\q").
	 */
	
	public void run() {

		Socket socket = null;
		InputStreamReader inputStreamReader = null;

		BufferedReader bufferedReader = null;

		try {

			socket = new Socket("localhost", 6668);
			System.out.println(
					"[LOADING] You are connected on Receiving Port! You can now Receive messages from the server.");
			inputStreamReader = new InputStreamReader(socket.getInputStream());

			bufferedReader = new BufferedReader(inputStreamReader);

			while (true) {

				String msgFromServer = bufferedReader.readLine();
				String msgFromServerDeCiphered = decryptCipher(msgFromServer, -2);
				String msgFromServerDeCrypted = decryptReverse(msgFromServerDeCiphered);

				System.out.println("Server says: " + msgFromServerDeCrypted);

				if (msgFromServerDeCrypted.equalsIgnoreCase("\\q")) {
					System.out.println("Server has left the chat.");
					break; 
				}

			}

		} catch (IOException e) {
			System.out.println("[LOADING FAILED] Failed to connect. Server is not online. Try again when server is running.");
			e.printStackTrace();
		}

	}

}
