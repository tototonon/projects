package praktikum01;

/**
 * WatchedFile class which is responsible for the right transitions.
 * @author Timon Tonon am 22.04.2020
 */
public class WatchedFile {

    /**
     * TransitionTable which represent the puml diagram.
     */
    //          CREATED,    MODIFIED ,    GONE,     DELETED,       INSYNC
    private static State transitionTable [] [] = {
            {State.INSYNC, State.INSYNC, State.GONE, State.GONE, State.INSYNC }, //SYNC
            {State.CREATED, State.MODIFIED, State.GONE,State.MODIFIED, State.MODIFIED}, //MODIFY
            {State.GONE, State.DELETED, State.GONE, State.DELETED, State.DELETED},//DELETE
            {State.CREATED, State.MODIFIED,State.CREATED, State.MODIFIED, State.MODIFIED} //CREATE
    };
    /**
     *
     * Init currentState of type State
     */
    private State currentState;

    /**
     *
     * @param currentState currentState of transitionTable
     * @param input current input Symbol
     * @return the exact place of actual State
     */

    private static State trigger (State currentState, Symbol input){
        return transitionTable[input.ordinal()][currentState.ordinal()];
    }

    /**
     *
     * @param input Symbol of actual status (create,modify,delete,sync).
     * @return currentState in which the transitionTable is.
     */
    public State transition (Symbol input){
        currentState = trigger(currentState,input);
        return currentState;
     }

    /**
     * Constructor to initialize entry point State.
     */
    public WatchedFile(String fileName) {
        currentState = State.CREATED;
    }

    /**
     * Constructor to initialize entry point State.
     */
    public WatchedFile() {
       currentState = State.CREATED;
    }

    /**
     *
     * @return currentState of Automat.
     */
    public State getCurrentState () {
        return currentState;
    }

}