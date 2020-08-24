package praktikum01;


import java.nio.file.Path;
import java.nio.file.WatchEvent;

import static java.nio.file.StandardWatchEventKinds.*;

/**
 * Enum which contains the Symbols to show the state of the transition.
 *
 * @author Timon Tonon on 28.04.2020
 */
public enum Symbol {
    SYNC("sync"), MODIFY("modify"), DELETE("delete"), CREATE("create");
    private String strName;

    /**
     * Constructor which assigns the String name.
     *
     * @param strName string name.
     */
    Symbol(String strName) {
        this.strName = strName;
    }

            public static Symbol parse(WatchEvent.Kind kind) {
            if (kind == ENTRY_CREATE) {
                return Symbol.CREATE;
            }
            if (kind == ENTRY_DELETE) {
                return Symbol.DELETE;
            }
            if (kind == ENTRY_MODIFY) {
                return Symbol.MODIFY;
            }
            return null;
        }
    }
