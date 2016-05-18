package veridu.storage;

/**
 * Class Storage
 */
public class Storage implements StorageInterface {
    private String token = null;
    private long expires = -1;
    private String username = null;

    /**
     * Sets the session token
     * 
     * @param token
     *            String
     * @return self
     */
    @Override
    public Storage setSessionToken(String token) {
        this.token = token;
        return this;
    }

    /**
     * Gets the session token
     * 
     * @return the token itself
     */
    @Override
    public String getSessionToken() {
        return this.token;
    }

    /**
     * Check if Session is Empty
     * 
     * @return boolean
     */
    @Override
    public boolean isSessionEmpty() {
        return this.token == null;
    }

    /**
     * Check if Username is Empty
     * 
     * @return boolean
     */
    @Override
    public boolean isUsernameEmpty() {
        boolean empty = this.username.isEmpty();
        return empty;
    }

    /**
     * Gets username
     * 
     * @return the username
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Sets username
     * 
     * @param username
     *            String
     * @return self
     */
    @Override
    public Storage setUsername(String username) {
        this.username = username;
        return this;
    }

    /**
     * deletes session
     * 
     * @return self
     */
    @Override
    public Storage purgeSession() {
        this.token = null;
        this.expires = -1;
        return this;
    }

    /**
     * Gets when session expires
     * 
     * @return the expires
     */
    @Override
    public long getSessionExpires() {
        return this.expires;
    }

    /**
     * Sets when session expires
     * 
     * @param expires
     *            String
     * @return self
     */
    @Override
    public Storage setSessionExpires(long expires) {
        this.expires = expires;
        return this;
    }

}
