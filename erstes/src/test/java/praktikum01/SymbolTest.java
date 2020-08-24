package praktikum01;


import org.junit.Before;
import org.junit.Test;

import java.nio.file.WatchEvent;

import static java.nio.file.StandardWatchEventKinds.*;
import static org.junit.Assert.assertSame;

/**
 * Test class for the Symbol enum
 * Tests the parse method.
 *
 * @author Timon Tonon on 28.04.2020
 */
public class SymbolTest {
    WatchEvent.Kind<?> kind1 = ENTRY_CREATE;
    WatchEvent.Kind<?> kind2 = ENTRY_DELETE;
    WatchEvent.Kind<?> kind3 = ENTRY_MODIFY;

    @Test
    public void parse() {
        assertSame(Symbol.CREATE, Symbol.parse(kind1));
        assertSame(Symbol.DELETE, Symbol.parse(kind2));
        assertSame(Symbol.MODIFY, Symbol.parse(kind3));

    }

}
