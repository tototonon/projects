import praktikum01.FileEvent;
import praktikum01.WatchedDirectory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Timon Tonon on 04.05.2020.
 * Class calls continiously sync(OutputStream out)
 * writes output to TCP-Socket of all connected clients.
 */

public class SyncServer extends Thread {
    /**
     * Variable for data
     */
    private WatchedDirectory watchedDirectory;

    /**
     * static Logger for the server
     */
    private static final Logger logger = Logger.getLogger(SyncServer.class.getName());
    /**
     * Init executor for Threadpool.
     */

    private ExecutorService executorService = Executors.newFixedThreadPool(10);

    /**
     * Init PORT for connection.
     */
    private static final int PORT = 4001;

    /**
     * Constructor of SyncServer class which creates a new Object of type WatchedDirecotory.
     *
     * And gives a variable of a BlockingQueue for Events to the class WatchedDirectory.
     * This is execute in a new Thread.
     * @param eventQueue is the BlockingQueue of FileEvents.
     */
    public SyncServer(BlockingQueue<FileEvent> eventQueue) {
        watchedDirectory = new WatchedDirectory(eventQueue);
        executorService.execute(watchedDirectory);
    }

    /**
     * Run method starts Server for PORT 4001.
     */
    public void run() {
        ServerSocket server = null;
        try {
            server = new ServerSocket(PORT);
            logger.info("Server started with ip " + InetAddress.getLocalHost().getHostAddress() + ":" + PORT);

            /**
             * Client accepted and new thread started.
             * executorService executes new SyncRunnable which runs the client and gives to him the watchedDirectory infos.
             */
            while (!Thread.currentThread().isInterrupted()) {
                Socket client = server.accept();
                String message = "New client with ip " + client.getInetAddress().getHostAddress() + ": " + client.getPort();
                logger.info(message);
                executorService.execute(() -> new SyncRunnable(watchedDirectory, client).run());
            }

        } catch (Exception e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        } finally {
            try {
                assert server != null;
                server.close();
            } catch (IOException e) {
                logger.info("Server closed");
            } catch (NullPointerException x) {
                logger.log(Level.WARNING, x.getMessage(), x);
            }
        }
    }
}


