package ie.gmit.dip;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class ServerThreadSendEncrypt extends Thread {
	
	/*
	 * The below method encrypts sent messages by reversing their string of
	 * characters (E.G., "Test123!" becomes "!321tseT").
	 */

	public String encryptReverse(String s) {
		StringBuilder sb = new StringBuilder();
		for (int i = s.length() - 1; i >= 0; i--) {
			sb.append(s.charAt(i));
		}

		return sb.toString();

	}

	/*
	 * The below method encrypts sent messages by Caesar cipher, shifting the
	 * decimal value of each character by the offset key. (E.G., with an offset of
	 * 2, 'a', which has the decimal value of 97, becomes 'c', which has the decimal
	 * value of 99).
	 */

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
	
	/*
	 * The below method creates a server socket which is bound to a hard coded port
	 * number. When a socket connection is made with the client on the other end, it
	 * creates an OutputStreamWriter and BufferedWriter to write to the socket, and
	 * a scanner to read console input. It then encrypts the strings entered into
	 * the console via our encryption methods above, sends them to the client in
	 * encrypted format over the network when the enter key is pressed, then flushes the
	 * bufferedWriter in order to read the next line entered to the console. This
	 * process repeats until the programme is stopped (by typing "\q"), in which case the 
	 * open socket connection is closed for this thread.
	 * 
	 * In this version of the programme, the host address (localhost) and Port Number
	 * are hard-coded. This can be easily changed by replacing localhost with an IP address, 
	 * or specifying an alternative port number (by hard coding each into the programme). 
	 * 
	 * This could be vastly improved by creating variables to store the IP address and port number, 
	 * and prompting the user to specify each at the beginning of the programmes run time. (Only the 
	 * client side will have to specify an IP address (the IP of this server). This will
	 * be implemented in later versions of the programme, but for now due to time limitations we
	 * are happy with the current version.
	 */

	public void run() {

		Socket socket = null;
		OutputStreamWriter outputStreamWriter = null;
		BufferedWriter bufferedWriter = null;
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket(6668);
		} catch (IOException e) {
			e.printStackTrace();
		}

			try {

				System.out.println("[LOADING] Waiting for client connection on Sending port...");
				socket = serverSocket.accept();
				System.out.println(
						"[LOADING] You are connected on Sending Port! You can now Send messages to the client.");

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
						System.out.println("You have left the chat. You can no longer send messages to client.");
						break;
					}

				}

				serverSocket.close();
				socket.close();
				outputStreamWriter.close();
				bufferedWriter.close();
				scanner.close();

			} catch (IOException e) {
				e.printStackTrace();
			}

	}

}
