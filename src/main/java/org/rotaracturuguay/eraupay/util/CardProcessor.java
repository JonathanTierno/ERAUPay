package org.rotaracturuguay.eraupay.util;



import com.google.gson.Gson;
import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.crypto.MACVerifier;

import java.nio.charset.Charset;
import java.security.SecureRandom;
import java.text.ParseException;

public class CardProcessor {

    private String key = "default";

    private CardProcessor() {}

    public static CardProcessor build() { return new CardProcessor(); }

    public CardProcessor setKey(String key) {
        this.key = key;
        return this;
    }

    public CardProcessor generateKey() {
        SecureRandom random = new SecureRandom();
        byte[] secret = new byte[32];
        random.nextBytes(secret);
        this.key = new String(secret, Charset.forName("UTF-8"));
        System.out.println(this.key);
        return this;
    }

    public String getWritablePayload(CardContents cc) throws JOSEException {
        //Convert Card Contents to JSON
        String payload = new Gson().toJson(cc);
        // Create HMAC signer
        JWSSigner signer = new MACSigner(this.key);
        // Prepare JWS object with payload
        JWSObject jwsObject = new JWSObject(new JWSHeader(JWSAlgorithm.HS256), new Payload(payload));
        // Apply the HMAC
        jwsObject.sign(signer);
        return jwsObject.serialize();
    }

    public boolean isPayloadValid(String payload) throws JOSEException {
        //TODO implement
        try {    // To parse the JWS and verify it, e.g. on client-side
            JWSObject jwsObject = JWSObject.parse(payload);
            JWSVerifier verifier = new MACVerifier(this.key);
            return jwsObject.verify(verifier);
        } catch (ParseException pex) {
            System.out.println("Parse exception of payload: " + payload);
            return false;
        } catch (JOSEException jex) {
            jex.printStackTrace();
            return false;
        }
    }

    public CardContents getContentsFromPayload(String payload) throws Exception {
        if (!isPayloadValid(payload)) {
            throw new Exception("Invalid Card Payload");
        }
        return new Gson().fromJson(JWSObject.parse(payload).getPayload().toString(), CardContents.class);
    }

}
