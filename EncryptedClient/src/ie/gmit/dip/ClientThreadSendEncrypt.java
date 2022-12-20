package ie.gmit.dip;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.Scanner;

public class ClientThreadSendEncrypt extends Thread {

	/* The below method encrypts sent messages by reversing their
	 string of characters (E.G., "Test123!" becomes "!321tseT"). */

	public String encryptReverse(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = s.length() - 1; i >= 0; i--) {
			sb.append(s.charAt(i));
		}

		return sb.toString();

	}

	/* The below method encrypts sent messages by Caesar cipher, shifting the
	 decimal value of each character by the offset key. (E.G., with an
	 offset of 2, 'a', which has the decimal value of 97, becomes 'c',
	 which has the decimal value of 99). */

	public String encryptCipher(String plainText, int offset) {
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
	
	/* The below method opens a socket connection on a hard coded host and port number.
	 * See comments in the Server side of the application in the class "ServerThreadSendEncrypt"
	 * for a more detailed breakdown. (The logic is almost identical).
	 */

	public void run() {

		Socket socket = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;

		try {

			socket = new Socket("localhost", 6669);
			System.out.println("[LOADING] You are connected on Sending Port! You can now Send messages to the server.");

			outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());

			bufferedWriter = new BufferedWriter(outputStreamWriter);

			Scanner scanner = new Scanner(System.in);

			System.out.println("[READY!] Enter your messages here in the console and hit enter to send. Type '\\q' to exit at any time.");

			while (true) {

				String msgToSend = scanner.nextLine();
				String reversedMessage = encryptReverse(msgToSend);
				String cipheredAndReversedMessage = encryptCipher(reversedMessage, 2);
				bufferedWriter.write(cipheredAndReversedMessage);
				bufferedWriter.newLine();
				bufferedWriter.flush();

				if (msgToSend.equalsIgnoreCase("\\q")) {
					System.out.println("You have left the chat. You can no longer send messages to the server.");
					
					break; 
				}

			}

			scanner.close();

		} catch (IOException e) {
			System.out.println("[LOADING FAILED] Failed to connect. Server is not online. Try again when Server is running.");
			e.printStackTrace();
		} finally {
			try {
				if (socket != null)
					socket.close();
				if (outputStreamWriter != null)
					outputStreamWriter.close();
				if (bufferedWriter != null)
					bufferedWriter.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
