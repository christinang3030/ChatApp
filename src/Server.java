import org.json.JSONObject;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;

/* The server application opens up a socket to listen in for messages from
 * clients, allowing users to join the chat, post a message, or leave the chat.
 */
public class Server {

    public static void main(String[] args) throws IOException {
        // Initialization
        DatagramSocket dgSocket = new DatagramSocket(8989);
        DatagramPacket buffer = new DatagramPacket(new byte[1000], 1000);

        // List of clients
        Map<String, UserInfo> clients = new HashMap<>();

        System.out.println("--- Server started ---");

        // Continue until server is shut down
        while (true) {
            try {
                // Receive message from client
                dgSocket.receive(buffer);

                // Create json message from byte[]
                JSONObject json = (new JSONObject(new String(buffer.getData())));

                // Get message details from json
                int type = json.getInt("type");
                String user = json.getString("user");
                String message = json.getString("message");

                // Check message type
                if (type == 0) { // if type == 'join' {save IP and port}
                    // Create new client and add to client list
                    UserInfo sender = new UserInfo(buffer.getAddress(), buffer.getPort());
                    clients.put(user, sender);
                    // Display client information
                    System.out.println("\n--- " + user + " joined the chat ---");
                    System.out.println("--- Saved entry: " + sender + " ---\n");
                    System.out.println(clients + "\n");
                } else if (type == 1) { // if type == 'post' {show the message content}
                    System.out.println(user + ": " + message);
                    // Send the message to each client in client list
                    for(UserInfo usr : clients.values()) {
                        String post = user + ": " + message;
                        byte[] broadcastMsg = post.getBytes();
                        DatagramPacket bfr = new DatagramPacket(
                                broadcastMsg, broadcastMsg.length, usr.getAddress(), usr.getPort());
                        dgSocket.send(bfr);
                    }
                } else if (type == 2) { // if type == 'leave' {remove IP and port}
                    System.out.println("\n--- " + user + " left the chat ---");
                    System.out.println("--- Removed entry: " + clients.get(user) + " ---\n");
                    // Remove client from client list
                    clients.remove(user);
                    System.out.println(clients + "\n");
                } else { // There should be no other message types
                    throw new IOException();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        // Close socket, release the port
        //dgSocket.close(); <- Never reached
    }
}

