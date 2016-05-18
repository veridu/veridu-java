package veridu.exceptions;

/**
 * Class EmptySession
 */
public class EmptySession extends Exception {

    public EmptySession() {
        super("Empty Session");
    }

    /**
     * Throws EmptySession Exception with message
     * 
     * @param msg
     *            String
     */
    public EmptySession(String msg) {
        super(msg);
    }
}