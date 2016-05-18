package com.veridu.storage;

public interface StorageInterface {

    public Storage setSessionToken(String token);

    public String getSessionToken();

    public Storage setUsername(String username);

    public String getUsername();

    public Storage setSessionExpires(long expires);

    public long getSessionExpires();

    public boolean isSessionEmpty();

    public boolean isUsernameEmpty();

    public Storage purgeSession();

}
