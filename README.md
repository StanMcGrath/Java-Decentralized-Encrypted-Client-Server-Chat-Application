# Java-Encrypted-Client-Server-Chat-Application
This programme represents a decentralized, peer-to-peer, encrypted chat application using Java Socket API. 

Contains one programme for each user (server and client).

//////////////

ABOUT

//////////////

The Server Side of the application opens a server socket connection on a hard-coded port, and listens for any incoming Client connections.
When a Client Connection is established, the Server opens two threads to send and recieve user-input String messages simultaneously. Before sending, the message is encrypted using a String reverser function and a Ceasar Cipher Algorithm. These encryption algorthms are simple but work as a proof of concept. When a message is recieved, the message is decrypted and printed to the console. 

The Client Side of the application attempts to connect to the Server ports. When a connection is established, it follows the same send and recieve message protocol as the Server side application mentioned above. 

//////////////

HOW TO RUN

//////////////

1) Via the command prompt on your OS (cmd on Windows), navigate to the bin directory of the Server Programme and run the following command:

java -cp ./Server.jar ie.gmit.dip.EncryptedServer

2) In the same way as above, navigate to the bin directory of the Client Programme and run the following command:

java -cp ./Client.jar ie.gmit.dip.EncryptedClient

NOTE: The Server programme must be run first. The Client programme will fail its connection untill the server is online. 


//////////////

TO DO:

//////////////

1: The current iteration of the progamme makes use of two ports and two threads to overcome blocking encounters during testing. This can and should be achieved using only one port and socket. The current iteration also only works on the Local host. This can easily be changed by allowing the user to input an IP address which will be implemented in the GUI version.

2: The port number is hard coded. This will be changed to a user-inputted variable through the GUI. 

3: Implement the GUI.

/


