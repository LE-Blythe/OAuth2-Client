package com.lcx.security.oauth2.impl;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.inject.Inject;
import com.lcx.security.oauth2.OAuth2AccessTokenStore;
import com.lcx.security.oauth2.exceptions.OAuth2AccessTokenInvalidException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Liam on 14/06/2015.
 */
public class OAuth2AccessTokenStorePersistent implements OAuth2AccessTokenStore {

    private static final String FILENAME = "tokens.json";

    private final Gson gson;
    Map<String, OAuth2AccessTokenMetaData> tokens;

    @Inject
    public OAuth2AccessTokenStorePersistent(Gson gson) throws IOException {
        tokens = new HashMap<String, OAuth2AccessTokenMetaData>();
        this.gson = gson;

        load();
    }

    public void addToken(OAuth2AccessToken token, OAuth2AccessTokenMetaData metaData) {
        tokens.put(token.getAcessToken(), metaData);
        store();
    }

    public void removeToken(OAuth2AccessToken token) {
        tokens.remove(token.getAcessToken());
        store();
    }

    public boolean exists(OAuth2AccessToken token) {
        return tokens.containsKey(token.getAcessToken());
    }

    public boolean isExpired(OAuth2AccessToken token) {
        try {
            return getMetaData(token).isExpired();
        } catch (OAuth2AccessTokenInvalidException e) {
            return false; //if token does not exists, it is not expired
        }
    }

    public OAuth2AccessTokenMetaData getMetaData(OAuth2AccessToken token) throws OAuth2AccessTokenInvalidException {

        if(token == null)
            throw new OAuth2AccessTokenInvalidException("No token provided");

        OAuth2AccessTokenMetaData metaData = tokens.get(token.getAcessToken());

        if(metaData == null)
            throw new OAuth2AccessTokenInvalidException("No metadata found for token");

        return metaData;
    }


    private void load() {

        File file = new File(FILENAME);

        if(!file.exists())
            return;

        try {
            byte[] encoded = Files.readAllBytes(Paths.get(file.toURI()));

            JsonArray tokenSet = readJson(new String(encoded));

            tokens.clear();

            for(int i=0; i < tokenSet.size(); i++){
                JsonObject o = tokenSet.get(i).getAsJsonObject();

                tokens.put(
                        o.get("token").getAsString(),
                        gson.fromJson(o.get("metadata"), OAuth2AccessTokenMetaData.class));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void store() {

        try {
            FileWriter file  = new FileWriter(FILENAME, false);

            try {
                file.write(buildJson().toString());
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                file.flush();
                file.close();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonArray buildJson() {

        JsonArray tokenSet = new JsonArray();

        for(Map.Entry<String, OAuth2AccessTokenMetaData> entry: tokens.entrySet()){

            JsonObject o = new JsonObject();
            o.addProperty("token", entry.getKey());
            o.add("metadata", gson.toJsonTree(entry.getValue()));

            tokenSet.add(o);
        }

        return tokenSet;
    }

    private JsonArray readJson(String fileStr){
        return gson.fromJson(fileStr, JsonArray.class);
    }
}
