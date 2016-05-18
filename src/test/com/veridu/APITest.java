package veridu;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import veridu.API;
import veridu.endpoint.Application;
import veridu.endpoint.Backplane;
import veridu.endpoint.Badge;
import veridu.endpoint.Batch;
import veridu.endpoint.Certificate;
import veridu.endpoint.Check;
import veridu.endpoint.Clone;
import veridu.endpoint.Credential;
import veridu.endpoint.Details;
import veridu.endpoint.Facts;
import veridu.endpoint.Hook;
import veridu.endpoint.Lookup;
import veridu.endpoint.OTP;
import veridu.endpoint.Password;
import veridu.endpoint.Personal;
import veridu.endpoint.Profile;
import veridu.endpoint.Provider;
import veridu.endpoint.Raw;
import veridu.endpoint.Request;
import veridu.endpoint.SSO;
import veridu.endpoint.Session;
import veridu.endpoint.State;
import veridu.endpoint.Task;
import veridu.endpoint.User;
import veridu.storage.Storage;

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
