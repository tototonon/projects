package praktikum01;

/**
 * Enum of States which defines the States in which the repository can be.
 */
public enum State {
    CREATED("created"), MODIFIED("modified"), GONE("gone"), DELETED("deleted"), INSYNC("insync");

    /**
     * Init of strName.
     */
    private String strName;

    /**
     * Constructor
     *
     * @param strName string .
     */
    State(String strName) {
        this.strName = strName;
    }

    /**
     * Method which parse the States CREATED, MODIFIED, GONE, DELETED, INSYNC to lower case.
     * more convenient way.
     *
     * @param name the States (CREATED, MODIFIED, GONE, DELETED, INSYNC).
     * @return null if name doesn 't exist.
     */

    public static State parse(String name) {
        String copy = name.toLowerCase();

        if (copy.equals(CREATED.toString())) {
            return CREATED;
        }
        if (copy.equals(MODIFIED.toString())) {
            return MODIFIED;
        }
        if (copy.equals(GONE.toString())) {
            return GONE;
        }
        if (copy.equals(DELETED.toString())) {
            return DELETED;
        }
        if (copy.equals(INSYNC.toString())) {
            return INSYNC;
        }
        return null;
    }

    /**
     * Returns string of states. If required.
     */
    @Override
    public String toString() {
        if (this == State.CREATED) {
            return "created";
        }
        if (this == State.MODIFIED) {
            return "modified";
        }
        if (this == State.GONE) {
            return "gone";
        }
        if (this == State.DELETED) {
            return "deleted";
        }
        if (this == State.INSYNC) {
            return "insync";
        }
        return "";
    }

}
