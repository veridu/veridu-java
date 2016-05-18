package veridu.exceptions;

/**
 * Class InvalidCertificate
 */
public class InvalidCertificate extends Exception {

    public InvalidCertificate() {
        super("Invalid Certificate");
    }

    /**
     * Throws InvalidCertificate Exception with message
     * 
     * @param msg
     *            String
     */
    public InvalidCertificate(String msg) {
        super(msg);
    }
}
