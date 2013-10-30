package com.veridu.sdk;

import com.veridu.sdk.Hex;
import java.security.SignatureException;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

//based on AWS' sample code for calculating HMAC-SHA1 signatures
//http://docs.aws.amazon.com/AWSSimpleQueueService/latest/SQSDeveloperGuide/AuthJavaSampleHMACSignature.html
public class HmacSHA1 {
    private static final String HMAC_SHA1_ALGORITHM = "HmacSHA1";
    
    /**
     * Computes RFC 2104-compliant HMAC signature.
     *
     * @param data
     * The data to be signed.
     * @param key
     * The signing key.
     * @return
     * The hex-encoded HMAC signature.
     * @throws
     * java.security.SignatureException when signature generation fails
     */
    public static String Calculate(String data, String key) throws java.security.SignatureException {
        String result = null;
        try {
            // get an hmac_sha1 key from the raw key bytes
            SecretKeySpec signingKey = new SecretKeySpec(key.getBytes(), HMAC_SHA1_ALGORITHM);

            // get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance(HMAC_SHA1_ALGORITHM);
            mac.init(signingKey);

            // compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(data.getBytes());
            return Hex.Encode(rawHmac);
        } catch (Exception e) {
            throw new SignatureException("Failed to generate HMAC : " + e.getMessage());
        }
    }
}
