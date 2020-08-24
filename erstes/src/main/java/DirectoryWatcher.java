import praktikum01.FileEvent;

import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * @author Timon Tonon on 04.05.2020
 * Class which observes an specific folder.
 * Whenever an event occurs inside the folder, which is specified by the path, it notifies about the new Event.
 */
public class DirectoryWatcher {
    /**
     * init Logger for DirectoryWatcher class.
     */
    private static final Logger logger = Logger.getLogger(DirectoryWatcher.class.getName());

    /**
     * Init executor variable of type ExecutorService for create ThreadPool of max 10 threads.
     */
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * Init BlockingQueue of type FileEvent named eventQueue.
     */

    private static final BlockingQueue<FileEvent> eventQueue = new LinkedBlockingQueue<>();
    /**
     * Init string variable for Path.
     */
    private String pathString;

    /**
     * Constructor of DirectoryWatcher class which obtains a pathString.
     * Logger prints out the path string of the directory to be watched.
     * Executor calls SyncServer and sends the eventQueue via BlockingQueue.
     *
     * @param pathString assigns the PathString to this class.
     */
    private DirectoryWatcher(String pathString) {
        logger.info("Directory to be watched: " + pathString);
        executor.execute(new SyncServer(eventQueue));
        this.pathString = pathString;
    }

    /**
     * Start method gets the Path string which is defined in pathString variable to WatchService which observes this specified folder.
     * It sends the file path via WatchService to method enqueueEvents.
     * Loggers for errors while sending, so thread will be interrupted.
     */
    private void start() {
        try {
            WatchService watchService = FileSystems.getDefault().newWatchService();
            Path path = Paths.get(pathString);
            path.register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
            boolean poll = true;
            while(poll) {
                poll = pollEvents(watchService);
            }

        } catch (IOException e) {
            logger.severe("Error while creating fileEvent object");
            logger.log(Level.WARNING, "context ", e);
            Thread.currentThread().interrupt();
        } catch (InterruptedException x) {
            logger.log(Level.WARNING, "Interrupted!", x);
            logger.info("Error");
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Main proofs if Path is in string array args, if so take the path at position [0] and start it, if not take the path of started directory(user.dir).
     *
     * @param args Array of String arguments of Directory to be watched.
     */
    public static void main(String[] args) {
        String pathString = args.length > 0 ? args[0] : System.getProperty("user.dir");
        DirectoryWatcher directoryWatcher = new DirectoryWatcher(pathString);
        directoryWatcher.start();

    }

    /**
     * Method PollEvents takes variable of type WatchService as param.
     * Iterates over WatchEvent list via WatchKey polling, pollEvents.
     * Sends variable of Type WatchEvents (watchEvent) to method enqueueEvent.
     * The interface WatchKey generates a key
     *
     * @param watchService variable contains path of dir to be watched.
     * @return true or false, if key is reset or not.
     * @throws InterruptedException if no valid key generated.
     */
    protected boolean pollEvents(WatchService watchService) throws InterruptedException {
        WatchKey key;
        key = watchService.take();
        List<WatchEvent<?>> list = key.pollEvents();
        for (WatchEvent<?> watchEvent : list) {
            enqueueEvent(watchEvent);
        }
        return key.reset();
    }

    /**
     * Method enqueueEvent takes variable watchEvent as param.
     * returns if null.
     * If not --> take watchEvent which contains the path and assigns it to pathEvent.
     * Create new FileEvent with the pathEvent of specific kind and add it to the evetQueue.
     * Logger prints out that new Event starts and the type of the Event.
     *
     * @param watchEvent variable which contains the path to be watched within the changes.
     */
    private void enqueueEvent(WatchEvent<?> watchEvent) {
        if (watchEvent == null) {
            return;
        }
        WatchEvent<Path> pathEvent = (WatchEvent<Path>) watchEvent;
        Path filename = pathEvent.context();
        FileEvent fileEvent = new FileEvent(filename.toString(), pathEvent.kind());
        eventQueue.add(fileEvent);
        logger.info("New event: " + fileEvent.getFilename() + " with type " + fileEvent.getEventType());
    }
}
