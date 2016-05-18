package veridu;

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

public class API {

    /**
     * String client unique ID
     */
    private String key;
    /**
     * String Shared secret
     */
    private String secret;
    /**
     * API version to be used
     */
    private String version;
    /**
     * Storage object for Session
     */

    private Storage storage = null;
    private Session session;
    private User user;
    private Personal personal;
    private Provider provider;
    private Profile profile;
    private Application application;
    private Backplane backplane;
    private Badge badge;
    private Batch batch;
    private Certificate certificate;
    private Check check;
    private Clone clone;
    private Credential credential;
    private Details details;
    private Facts facts;
    private Hook hook;
    private Lookup lookup;
    private OTP otp;
    private Password password;
    private Raw raw;
    private Request request;
    private SSO sso;
    private State state;
    private Task task;

    /**
     * Returns a new API instance
     *
     * @param key
     *            String Client KEY
     * @param secret
     *            String Client SECRET
     * @param version
     *            String API Version
     *
     * @return API object
     */
    public static API factory(String key, String secret, String version) {
        Storage storage = new Storage();
        return new API(key, secret, version, storage);
    }

    /**
     * Class Constructor
     *
     * @param key
     *            String Client ID
     * @param secret
     *            String Client Secret
     * @param version
     *            String API Version
     * @param storage
     *            Storage object
     */
    public API(String key, String secret, String version, Storage storage) {
        this.setKey(key);
        this.setSecret(secret);
        this.setVersion(version);
        this.setStorage(storage);
    }

    /**
     * Gets the Client Id
     *
     * @return key
     */
    public final String getKey() {
        return key;
    }

    /**
     * Sets the Client Id
     *
     * @param key
     *            String
     */
    public final void setKey(String key) {
        this.key = key;
    }

    /**
     * Gets the Secret
     *
     * @return secret
     */
    public final String getSecret() {
        return secret;
    }

    /**
     * Sets secret
     *
     * @param secret
     *            String
     */
    public final void setSecret(String secret) {
        this.secret = secret;
    }

    /**
     * Gets Version of API
     *
     * @return version
     */
    public final String getVersion() {
        return version;
    }

    /**
     * Sets Version of API
     *
     * @param version
     *            String
     */
    public final void setVersion(String version) {
        this.version = version;
    }

    /**
     * Gets storage object
     *
     * @return Storage storage
     */
    public final Storage getStorage() {
        return storage;
    }

    /**
     * Sets storage object
     *
     * @param storage
     *            Storage
     */
    public final void setStorage(Storage storage) {
        this.storage = storage;
    }

    /**
     * Instantiates Application
     *
     * @see veridu.endpoint.Application
     * @return application Application
     * @see <a href="https://veridu.com/wiki/Application_Resource">Application
     *      Resource</a>
     */
    public Application getApplication() {
        if (!(this.application instanceof Application)) {
            this.application = new Application(this.key, this.secret, this.version, this.storage);
        }
        return this.application;
    }

    /**
     * Instantiates Backplane
     *
     * @see veridu.endpoint.Backplane
     * @return backplane Backplane
     * @see <a href="https://veridu.com/wiki/Backplane_Resource">Backplane
     *      Resource</a>
     */
    public Backplane getBackplane() {
        if (!(this.backplane instanceof Backplane)) {
            this.backplane = new Backplane(this.key, this.secret, this.version, this.storage);
        }
        return this.backplane;
    }

    /**
     * Instantiates Badge
     *
     * @see veridu.endpoint.Badge
     * @return badge Badge
     * @see <a href="https://veridu.com/wiki/Badge_Resource">Badge Resource</a>
     */
    public Badge getBadge() {
        if (!(this.badge instanceof Badge)) {
            this.badge = new Badge(this.key, this.secret, this.version, this.storage);
        }
        return this.badge;
    }

    /**
     * Instantiates Batch
     *
     * @see veridu.endpoint.Batch
     * @return batch Batch
     * @see <a href="https://veridu.com/wiki/Batch_Resource">Batch Resource</a>
     */
    public Batch getBatch() {
        if (!(this.batch instanceof Batch)) {
            this.batch = new Batch(this.key, this.secret, this.version, this.storage);
        }
        return this.batch;
    }

    /**
     * Instantiates Certificate
     *
     * @see veridu.endpoint.Certificate
     * @return certificate Certificate
     * @see <a href="https://veridu.com/wiki/Certificate_Resource">Certificate
     *      Resource</a>
     */
    public Certificate getCertificate() {
        if (!(this.certificate instanceof Certificate)) {
            this.certificate = new Certificate(this.key, this.secret, this.version, this.storage);
        }
        return this.certificate;
    }

    /**
     * Instantiates Check
     *
     * @see veridu.endpoint.Check
     * @return check Check
     * @see <a href="https://veridu.com/wiki/Check_Resource">Check Resource</a>
     */
    public Check getCheck() {
        if (!(this.check instanceof Check)) {
            this.check = new Check(this.key, this.secret, this.version, this.storage);
        }
        return this.check;
    }

    /**
     * Instantiates Clone
     *
     * @see veridu.endpoint.Clone
     * @return clone Clone
     * @see <a href="https://veridu.com/wiki/Clone_Resource">Clone Resource</a>
     */
    public Clone getClone() {
        if (!(this.clone instanceof Clone)) {
            this.clone = new Clone(this.key, this.secret, this.version, this.storage);
        }
        return this.clone;
    }

    /**
     * Instantiates Credential
     *
     * @see veridu.endpoint.Credential
     * @return credential Credential
     * @see <a href="https://veridu.com/wiki/Credential_Resource">Credential
     *      Resource</a>
     */
    public Credential getCredential() {
        if (!(this.credential instanceof Credential)) {
            this.credential = new Credential(this.key, this.secret, this.version, this.storage);
        }
        return this.credential;
    }

    /**
     * Instantiates Details
     *
     * @see veridu.endpoint.Details
     * @return details Details
     * @see <a href="https://veridu.com/wiki/Details_Resource">Details
     *      Resource</a>
     */
    public Details getDetails() {
        if (!(this.details instanceof Details)) {
            this.details = new Details(this.key, this.secret, this.version, this.storage);
        }
        return this.details;
    }

    /**
     * Instantiates Facts
     *
     * @see veridu.endpoint.Facts
     * @return facts Facts
     * @see <a href="https://veridu.com/wiki/Facts_Resource">Facts Resource</a>
     */
    public Facts getFacts() {
        if (!(this.facts instanceof Facts)) {
            this.facts = new Facts(this.key, this.secret, this.version, this.storage);
        }
        return this.facts;
    }

    /**
     * Instantiates Hook
     *
     * @see veridu.endpoint.Hook
     * @return hook Hook
     * @see <a href="https://veridu.com/wiki/Hook_Resource">Hook Resource</a>
     */
    public Hook getHook() {
        if (!(this.hook instanceof Hook)) {
            this.hook = new Hook(this.key, this.secret, this.version, this.storage);
        }
        return this.hook;
    }

    /**
     * Instantiates Lookup
     *
     * @see veridu.endpoint.Lookup
     * @return lookup Lookup
     * @see <a href="https://veridu.com/wiki/Lookup_Resource">Lookup
     *      Resource</a>
     */
    public Lookup getLookup() {
        if (!(this.lookup instanceof Lookup)) {
            this.lookup = new Lookup(this.key, this.secret, this.version, this.storage);
        }
        return this.lookup;
    }

    /**
     * Instantiates OTP
     *
     * @see veridu.endpoint.OTP
     * @return otp OTP
     * @see <a href="https://veridu.com/wiki/OTP_Resource">OTP Resource</a>
     */
    public OTP getOTP() {
        if (!(this.otp instanceof OTP)) {
            this.otp = new OTP(this.key, this.secret, this.version, this.storage);
        }
        return this.otp;
    }

    /**
     * Instantiates Password
     *
     * @see veridu.endpoint.Password
     * @return password Password
     * @see <a href="https://veridu.com/wiki/Password_Resource">Password
     *      Resource</a>
     */
    public Password getPassword() {
        if (!(this.password instanceof Password)) {
            this.password = new Password(this.key, this.secret, this.version, this.storage);
        }
        return this.password;
    }

    /**
     * Instantiates Personal
     *
     * @see veridu.endpoint.Personal
     * @return personal Personal
     * @see <a href="https://veridu.com/wiki/Personal_Resource">Personal
     *      Resource</a>
     */
    public Personal getPersonal() {
        if (!(this.personal instanceof Personal)) {
            this.personal = new Personal(this.key, this.secret, this.version, this.storage);
        }
        return this.personal;

    }

    /**
     * Instantiates Profile
     *
     * @see veridu.endpoint.Profile
     * @return profile Profile
     * @see <a href="https://veridu.com/wiki/Profile_Resource">Profile
     *      Resource</a>
     */
    public Profile getProfile() {
        if (!(this.profile instanceof Profile)) {
            this.profile = new Profile(this.key, this.secret, this.version, this.storage);
        }
        return this.profile;
    }

    /**
     * Instantiates Provider
     *
     * @see veridu.endpoint.Provider
     * @return provider Provider
     * @see <a href="https://veridu.com/wiki/Provider_Resource">Provider
     *      Resource</a>
     */
    public Provider getProvider() {
        if (!(this.provider instanceof Provider)) {
            this.provider = new Provider(this.key, this.secret, this.version, this.storage);
        }
        return this.provider;

    }

    /**
     * Instantiates Raw
     *
     * @see veridu.endpoint.Raw
     * @return raw Raw
     * @see <a href="https://veridu.com/wiki/Raw_Resource">Raw Resource</a>
     */
    public Raw getRaw() {
        if (!(this.raw instanceof Raw)) {
            this.raw = new Raw(this.key, this.secret, this.version, this.storage);
        }
        return this.raw;
    }

    /**
     * Instantiates Request
     *
     * @see veridu.endpoint.Request
     * @return request Request
     * @see <a href="https://veridu.com/wiki/Request_Resource">Request
     *      Resource</a>
     */
    public Request getRequest() {
        if (!(this.request instanceof Request)) {
            this.request = new Request(this.key, this.secret, this.version, this.storage);
        }
        return this.request;
    }

    /**
     * Instantiates Session
     *
     * @see veridu.endpoint.Session
     * @return session Session
     * @see <a href="https://veridu.com/wiki/Session_Resource">Session
     *      Resource</a>
     */
    public Session getSession() {
        if (!(this.session instanceof Session)) {
            this.session = new Session(this.key, this.secret, this.version, this.storage);
        }
        return this.session;
    }

    /**
     * Instantiates SSO
     *
     * @see veridu.endpoint.SSO
     * @return sso SSO
     * @see <a href="https://veridu.com/wiki/SSO_Resource">SSO Resource</a>
     */
    public SSO getSSO() {
        if (!(this.sso instanceof SSO)) {
            this.sso = new SSO(this.key, this.secret, this.version, this.storage);
        }
        return this.sso;
    }

    /**
     * Instantiates State
     *
     * @see veridu.endpoint.State
     * @return state State
     * @see <a href="https://veridu.com/wiki/State_Resource">State Resource</a>
     */
    public State getState() {
        if (!(this.state instanceof State)) {
            this.state = new State(this.key, this.secret, this.version, this.storage);
        }
        return this.state;
    }

    /**
     * Instantiates Task
     *
     * @see veridu.endpoint.Task
     * @return task Task
     * @see <a href="https://veridu.com/wiki/Task_Resource">Task Resource</a>
     */
    public Task getTask() {
        if (!(this.task instanceof Task)) {
            this.task = new Task(this.key, this.secret, this.version, this.storage);
        }
        return this.task;
    }

    /**
     * Instantiates User
     *
     * @see veridu.endpoint.User
     * @return user User
     * @see <a href="https://veridu.com/wiki/User_Resource">User Resource</a>
     */
    public User getUser() {
        if (!(this.user instanceof User)) {
            this.user = new User(this.key, this.secret, this.version, this.storage);
        }
        return this.user;
    }

}
