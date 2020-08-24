package praktikum01;

import java.nio.file.Path;
import java.nio.file.WatchEvent;

/**
 * @author Timon Tonon on 08.05.2020.
 * FileEvent class takes creates FileEvent objects for DirectoryWatcher class.
 */

public class FileEvent {
    /**
     * Init variable for filename string.
     */
    private String filename;
    /**
     * Init variable of type Symbol for eventType.
     */

    private Symbol eventType;

    /**
     * Constructor of FileEvent class, which takes a string of filename and an eventType path of
     * the interface WatchEvent as parameters. Error if eventType is null.
     * @param filename is assigned by  string filename.
     * @param eventType is asssigned by parsed Symbol.
     */
    public FileEvent(String filename, WatchEvent.Kind<Path> eventType) {
        this.filename = filename;
        this.eventType = Symbol.parse(eventType);
        if (this.eventType == null) {
            System.err.println("eventType could not be parsed, wrong Kind value");
        }
    }

    /**
     * Getter method for filename.
     * @return filename.
     */
    public String getFilename() {
        return filename;
    }
    /**
     * Getter method for eventType.
     * @return parsed eventType.
     */
    public Symbol getEventType() {
        return eventType;
    }

}
