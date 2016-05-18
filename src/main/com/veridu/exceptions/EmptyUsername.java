package veridu.exceptions;

/**
 * Class EmptyUsername
 */
public class EmptyUsername extends Exception {

    public EmptyUsername() {
        super("Empty Username");
    }

    /**
     * Throws EmptyUsername Exception with message
     * 
     * @param msg
     *            String
     */
    public EmptyUsername(String msg) {
        super(msg);
    }
}
