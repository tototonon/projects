import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Timon Tonon on 04.05.20
 * Class connect via Socket with class SyncServer.
 * Receives data from SyncServer via socket and prints them out to console.
 */
public class SyncClient {
    /**
     * TCP- Socket API
     */
    private Socket socket;

    /**
     * Init Logger
     */
    private static final Logger logger = Logger.getLogger(SyncClient.class.getName());

    /**
     * Constructor. Connects via socket with PORT 4001 and Localhost.
     */
    private SyncClient() {
        try {
            this.socket = new Socket(InetAddress.getLocalHost(), 4001);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Could not create client socket", e);
        }
    }

    /**
     * Connect method connects via socket to SyncServer with ip host and Port.
     * Gets input Stream from the socket and stores it in String Array.
     * If socket is null return.
     */
    public void connect() {
        if (socket == null) {
            return;
        }

        BufferedReader bufferedReader;
        String readPacket = "";
        System.out.println("Connecting to server");
        /**
         * While thread isn't interrupted, create InputStreamReader
         * to wrap InputStream for the socket via BufferedReader.
         */
        while (!Thread.currentThread().isInterrupted()) {
            try {

                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                char[] buffer = new char[256];
                int bufferLength = bufferedReader.read(buffer, 0, 256);
                readPacket = new String(buffer, 0, bufferLength);
            } catch (SocketException e) {
                String message = "Client disconnected with ip " + socket.getInetAddress().getHostAddress()
                        + ":" + socket.getPort();
                logger.info(message);
                return;

            } catch (IOException e) {
                logger.severe("Error while trying to write data into buffer");
                return;
            }
            System.out.println(readPacket);
        }
    }

    /**
     * Main creates new Client and connect.
     *
     * @param args string array.
     */
    public static void main(String[] args) {
        SyncClient client = new SyncClient();
        client.connect();
    }
}
