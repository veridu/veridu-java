package veridu.exceptions;

/**
 * Class RequestFailed
 */
public class RequestFailed extends Exception {

    public RequestFailed() {
        super("Request Failed");
    }

    /**
     * Throws RequestFailed Exception with message
     *
     * @param msg
     *            String
     */
    public RequestFailed(String msg) {
        super(msg);
    }
}
