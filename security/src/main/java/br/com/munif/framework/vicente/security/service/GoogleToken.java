/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.framework.vicente.security.service;

import com.google.api.client.googleapis.apache.v2.GoogleApacheHttpTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author munif
 */
public class GoogleToken {

    public static String CLIENT_ID = "420476629164-ovplq9e3glapobvk5hnah37sv470b9sv.apps.googleusercontent.com";

    public static Map verify(String token) {
        Map<String, Object> response = new HashMap();

        try {
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(GoogleApacheHttpTransport.newTrustedTransport(), new GsonFactory())
                    // Specify the CLIENT_ID of the app that accesses the backend:
                    .setAudience(Collections.singletonList(CLIENT_ID))
                    // Or, if multiple clients access the backend:
                    //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                    .build();

            // (Receive idTokenString by HTTPS POST)
            GoogleIdToken idToken = verifier.verify(token);
            if (idToken != null) {
                Payload payload = idToken.getPayload();
                response.put("userId", payload.getSubject());

                response.putAll(payload);

                // Get profile information from payload
                response.put("email", payload.getEmail());
                response.put("emailVerified", Boolean.valueOf(payload.getEmailVerified()));
                response.put("name", (String) payload.get("name"));
                response.put("pictureUrl", (String) payload.get("picture"));
                response.put("locale", (String) payload.get("locale"));
                response.put("familyName", (String) payload.get("family_name"));
                response.put("givenName", (String) payload.get("given_name"));


            } else {
                response.put("exception", "Invalid ID token.");
            }
        } catch (GeneralSecurityException | IOException ex) {
            Logger.getLogger(GoogleToken.class.getName()).log(Level.SEVERE, null, ex);
            response.put("exception", ex);
        }
        return response;

    }

}
