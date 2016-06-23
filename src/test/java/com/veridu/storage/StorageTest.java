package com.veridu.storage;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.veridu.storage.Storage;

public class StorageTest {
    Storage storage = new Storage();

    @Test
    public void testIsSessionEmptyReturnsFalse() {
        this.storage.setSessionToken("token");
        assertFalse(this.storage.isSessionEmpty());
    }

    @Test
    public void testIsSessionEmptyReturnsTrue() {
        this.storage.purgeSession();
        assertTrue(this.storage.isSessionEmpty());
    }

    @Test
    public void testIsUsernameEmptyReturnsFalse() {
        this.storage.setUsername("user");
        assertFalse(this.storage.isUsernameEmpty());
    }

    @Test
    public void testIsUsernameEmptyReturnsTrue() {
        this.storage.setUsername("");
        assertTrue(this.storage.isUsernameEmpty());
    }

    @Test
    public void testPurgeSession() {
        this.storage.setSessionToken("token");
        this.storage.setSessionExpires(2);
        assertEquals("token", this.storage.getSessionToken());
        assertEquals(2, this.storage.getSessionExpires());
        assertTrue(this.storage.purgeSession() instanceof Storage);
        assertNull(this.storage.getSessionToken());
        assertEquals(-1, this.storage.getSessionExpires());
    }

}
