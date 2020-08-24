package praktikum01;

import org.junit.Before;
import org.junit.Test;

import java.util.logging.Logger;

import static org.junit.Assert.*;

/**
 * Test class for the State enum
 * Tests the parse to string
 *
 * @author Timon Tonon on 04.05.2020
 */
public class StateTest {


    //State [] states = {State.MODIFIED, State.CREATED, State.DELETED, State.GONE, State.INSYNC};
    private String state1;
    private String state2;
    private String state3;
    private String state4;
    private String state5;

    /**
     * Setup State strings
     */
    @Before
    public void setUp() {


        state1 = "created";
        state2 = "modified";
        state3 = "gone";
        state4 = "deleted";
        state5 = "insync";
    }

    /**
     * Test if value sync can be parsed to a string.
     */
    @Test
    public void parse() {
            assertSame(State.CREATED, State.parse(state1));
            assertSame(State.MODIFIED, State.parse(state2));
            assertSame(State.GONE, State.parse(state3));
            assertSame(State.DELETED, State.parse(state4));
            assertSame(State.INSYNC, State.parse(state5));

    }
}
