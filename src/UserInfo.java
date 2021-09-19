import java.net.InetAddress;

/*
 * UserInfo stores IP address and port number of a user.
 */
public class UserInfo {
    // Instance variables
    private InetAddress ipAddress;
    private int portNum;

    public UserInfo(InetAddress ip, int p) {
        ipAddress = ip;
        portNum = p;
    }

    // Helpful methods
    public InetAddress getAddress() { return ipAddress; }
    public int getPort() { return portNum; }
    @Override
    public String toString() {
        return "[IP address = " + ipAddress +
                ", port number = " + portNum + "]";
    }
}

