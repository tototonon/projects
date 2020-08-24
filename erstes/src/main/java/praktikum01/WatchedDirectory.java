package praktikum01;

import com.google.gson.Gson;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class WatchedDirectory manages a Map of objects of type WatchedFile.It runs in own thread.
 *
 * @author Timon Tonon on 07.05.2020
 */
public class WatchedDirectory extends Thread {
    /**
     * Init List of Type WatchedFile.
     */
    protected Map<String, WatchedFile> watchedFileMap = new HashMap<>();

    /**
     * Executor for Thread.
     */
    private static final ExecutorService executor = Executors.newFixedThreadPool(10);

    /**
     * Init static Logger to log errors and write it to log data.
     */
    private static final Logger logger = Logger.getLogger(WatchedDirectory.class.getName());
    /**
     * Init BlockingQueue for FileEvents.
     */
    private BlockingQueue<FileEvent> eventQueue;

    /**
     * Constructor of WatchedDirectory class which takes the BlockingQueue as params.
     *
     * @param eventQueue assigns eventQueue to this class.
     */
    public WatchedDirectory(BlockingQueue<FileEvent> eventQueue) {
        this.eventQueue = eventQueue;
    }

    /**
     * Run method with loop that force the thread to pause/sleep for 10 secs.
     * For iterates over the eventQueue and writes it into the update method.
     */
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.log(Level.WARNING, "Interrupted!", e);
                Thread.currentThread().interrupt();
            }
            for (FileEvent fileEvent : eventQueue) {
                update(fileEvent);
            }
        }
    }

    /**
     * Update method which takes a FileEvent object as param.
     * This method takes the values of the WatchedFileMap into variable file of type WatchedFile.
     * If Map doesn't contain the key it creates new WatchedFile object and writes it into file of tpe WatchedFile.
     * If file not null it makes the transition in the automat.
     *
     * @param fileEvent variable of the object type FileEvent.
     */
    public void update(FileEvent fileEvent) {
        String fileName = fileEvent.getFilename();
        WatchedFile file;
        if (!watchedFileMap.containsKey(fileName)) {
            file = new WatchedFile(fileName);
            watchedFileMap.put(fileName, file);
        } else {
            file = watchedFileMap.get(fileName);
        }
        if (file != null) {
            file.transition(fileEvent.getEventType());
        }
    }


    /**
     * Sync method takes an OutputStream as param.
     * Ouput with Json string. This method sends the Symbol SYNC to all saved WatchedFile objects.
     *
     * @param outputStream streams out WatchedFile objects.
     */
    public void sync(OutputStream outputStream) {
        PrintWriter printWriter = new PrintWriter(outputStream);
        for (WatchedFile watchedFile : watchedFileMap.values()) {
            Gson gson = new Gson();
            String json = gson.toJson(watchedFile);
            watchedFile.transition(Symbol.SYNC);
            printWriter.print(json);
            printWriter.flush();

        }


    }
}