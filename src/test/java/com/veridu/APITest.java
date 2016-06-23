package com.veridu;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.veridu.API;
import com.veridu.endpoint.Application;
import com.veridu.endpoint.Backplane;
import com.veridu.endpoint.Badge;
import com.veridu.endpoint.Batch;
import com.veridu.endpoint.Certificate;
import com.veridu.endpoint.Check;
import com.veridu.endpoint.Clone;
import com.veridu.endpoint.Credential;
import com.veridu.endpoint.Details;
import com.veridu.endpoint.Facts;
import com.veridu.endpoint.Hook;
import com.veridu.endpoint.Lookup;
import com.veridu.endpoint.OTP;
import com.veridu.endpoint.Password;
import com.veridu.endpoint.Personal;
import com.veridu.endpoint.Profile;
import com.veridu.endpoint.Provider;
import com.veridu.endpoint.Raw;
import com.veridu.endpoint.Request;
import com.veridu.endpoint.SSO;
import com.veridu.endpoint.Session;
import com.veridu.endpoint.State;
import com.veridu.endpoint.Task;
import com.veridu.endpoint.User;
import com.veridu.storage.Storage;

public class APITest {

    String key = "key";
    String secret = "secret";
    String version = "version";
    Storage storage = new Storage();
    API api = API.factory(key, secret, version);

    @Test
    public void testFactoryReturnsAPIInstance() {
        API factoryapi = API.factory(key, secret, version);
        assertTrue(factoryapi instanceof API);
    }

    @Test
    public void testGetApplication() {
        Object endpoint = this.api.getApplication();
        assertTrue(endpoint instanceof Application);
    }

    @Test
    public void testGetBackplane() {
        Backplane endpoint = this.api.getBackplane();
        assertTrue(endpoint instanceof Backplane);
    }

    @Test
    public void testGetBadge() {
        Badge endpoint = this.api.getBadge();
        assertTrue(endpoint instanceof Badge);
    }

    @Test
    public void testGetBatch() {
        Batch endpoint = this.api.getBatch();
        assertTrue(endpoint instanceof Batch);
    }

    @Test
    public void testGetCertificate() {
        Certificate endpoint = this.api.getCertificate();
        assertTrue(endpoint instanceof Certificate);
    }

    @Test
    public void testGetCheck() {
        Check endpoint = this.api.getCheck();
        assertTrue(endpoint instanceof Check);
    }

    @Test
    public void testGetClone() {
        Clone endpoint = this.api.getClone();
        assertTrue(endpoint instanceof Clone);
    }

    @Test
    public void testGetCredential() {
        Credential endpoint = this.api.getCredential();
        assertTrue(endpoint instanceof Credential);
    }

    @Test
    public void testGetDetails() {
        Details endpoint = this.api.getDetails();
        assertTrue(endpoint instanceof Details);
    }

    @Test
    public void testGetFacts() {
        Facts endpoint = this.api.getFacts();
        assertTrue(endpoint instanceof Facts);
    }

    @Test
    public void testGetHook() {
        Hook endpoint = this.api.getHook();
        assertTrue(endpoint instanceof Hook);
    }

    @Test
    public void testGetLookup() {
        Lookup endpoint = this.api.getLookup();
        assertTrue(endpoint instanceof Lookup);
    }

    @Test
    public void testGetOTP() {
        OTP endpoint = this.api.getOTP();
        assertTrue(endpoint instanceof OTP);
    }

    @Test
    public void testGetPassword() {
        Password endpoint = this.api.getPassword();
        assertTrue(endpoint instanceof Password);
    }

    @Test
    public void testGetPersonal() {
        Personal endpoint = this.api.getPersonal();
        assertTrue(endpoint instanceof Personal);
    }

    @Test
    public void testGetProfile() {
        Profile endpoint = this.api.getProfile();
        assertTrue(endpoint instanceof Profile);
    }

    @Test
    public void testGetProvider() {
        Provider endpoint = this.api.getProvider();
        assertTrue(endpoint instanceof Provider);
    }

    @Test
    public void testGetRaw() {
        Raw endpoint = this.api.getRaw();
        assertTrue(endpoint instanceof Raw);
    }

    @Test
    public void testGetRequest() {
        Request endpoint = this.api.getRequest();
        assertTrue(endpoint instanceof Request);
    }

    @Test
    public void testGetSession() {
        Session endpoint = this.api.getSession();
        assertTrue(endpoint instanceof Session);
    }

    @Test
    public void testGetSSO() {
        SSO endpoint = this.api.getSSO();
        assertTrue(endpoint instanceof SSO);
    }

    @Test
    public void testGetState() {
        State endpoint = this.api.getState();
        assertTrue(endpoint instanceof State);
    }

    @Test
    public void testGetTask() {
        Task endpoint = this.api.getTask();
        assertTrue(endpoint instanceof Task);
    }

    @Test
    public void testGetUser() {
        User endpoint = this.api.getUser();
        assertTrue(endpoint instanceof User);
    }

}
