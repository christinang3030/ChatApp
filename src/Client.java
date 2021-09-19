import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import org.json.JSONObject;

/* In order to handle different types of messages, we will define a
 * message format using json object.
 *      Message type: integer (0 - join, 1 - post, 2 - leave)
 *      User name: String
 *      Message content: String
 */

/* The Client class is an application that allows a user to join a chat
 * through the server and send messages to other chat participants.
 */
public class Client {

    public static void main(String[] args) throws IOException {
        // Initialization
        DatagramSocket dgSocket = new DatagramSocket();
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Client created ---\n");

        // Get user name
        System.out.println("Enter a username: ");
        String user = sc.nextLine();

        // User is joining the chat
        int type = 0;

        // Create new message in json format
        JSONObject info = new JSONObject();
        info.put("type", type);
        info.put("user", user);
        info.put("message", "");

        // json object converted to byte array
        byte[] data = info.toString().getBytes();

        // Packet includes message, client IP address, and port number
        DatagramPacket buffer = new DatagramPacket(
                data, data.length, InetAddress.getLocalHost(), 8989);

        // Send packet to server
        dgSocket.send(buffer);

        // Create new thread to listen for messages from server
        Thread messageListener = new Thread() {
            public void run() {
                while (true) {
                    try {
                        // Print messages sent from the server
                        DatagramPacket bfr = new DatagramPacket(new byte[1000], 1000);
                        dgSocket.receive(bfr); // Same UDP socket is used
                        System.out.println(new String(bfr.getData()));
                    } catch (SocketException se) { // Socket closed after leaving chat
                        interrupt();
                        return; // End thread
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        messageListener.start(); // Start thread

        System.out.println("\n--- Joined chat ---\n--- Start chatting! Type ':leave' to quit chat ---\n");

        // Continue to send messages until client requests to leave
        boolean leave = false;
        while (!leave) {
            // Read the next message
            String msg = sc.nextLine();

            // Check if client requested to leave
            if (msg.equalsIgnoreCase(":leave")) {
                type = 2; // leave type
                leave = true; // exit loop
            } else {
                type = 1; // post type
            }

            // Create new message
            JSONObject nextMsg = new JSONObject();
            nextMsg.put("type", type);
            nextMsg.put("user", user);
            nextMsg.put("message", msg);

            // json object converted to byte array
            data = nextMsg.toString().getBytes();

            // Packet includes message, client IP address, and port number
            DatagramPacket nextBfr = new DatagramPacket(
                    data, data.length, InetAddress.getLocalHost(), 8989);

            // Send packet to server
            dgSocket.send(nextBfr);
        }

        System.out.println("--- Left chat ---");

        // Close socket, release port
        dgSocket.close();
    }
}