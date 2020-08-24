import praktikum01.WatchedDirectory;

import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Timon Tonon on 08.05.2020
 * Overwrites Interface runnable.
 * Need this class to not overwrite client threads.
 */
public class SyncRunnable implements Runnable {
    /**
     * Init Logger for SyncRunnable class.
     */
    private Logger logger = Logger.getLogger(SyncRunnable.class.getName());
    /**
     * Init variable of type WatchedDirectory.
     */
    private WatchedDirectory watchedDirectory;
    /**
     * Init Socker for client.
     */
    private Socket client;

    /**
     * Constructor of SyncRunnable class.
     * @param watchedDirectory variable of type WatchedDirectory to call the method sync in there.
     * @param client variable for the client Socket to connect.
     */
    public SyncRunnable(WatchedDirectory watchedDirectory, Socket client) {
        this.watchedDirectory = watchedDirectory;
        this.client = client;
    }

    /**
     * Run method forces current threads to call every 10 secs sync method
     * and connect to sync method in watchedDirectory sending Outputstream.
     */
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1000);
                watchedDirectory.sync(client.getOutputStream());
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error in SyncRunnable thread ", e);
            }
        }
    }
}
