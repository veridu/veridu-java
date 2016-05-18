package veridu.exceptions;

/**
 * Class UnavailableRegion
 */
public class UnavailableRegion extends Exception{
   	public UnavailableRegion() {
        super("Unavailable Region");
    }
    
    /**
     * Throws UnavailableRegion Exception with message
     *
     * @param msg String
     */
    public UnavailableRegion(String msg) {
        super(msg);
    }

}
