package com.wire.bots.gitlab;

import com.wire.bots.sdk.Configuration;

import java.util.UUID;

public class Database {
    private final UUID botId;
    private final RedisClient redisClient;

    public Database(String botId) {
        this.botId = UUID.fromString(botId);
        Configuration.DB db = Service.config.db;
        redisClient = new RedisClient(db.host, db.port, db.password, db.timeout);
    }

    boolean insertSecret(String secret) {
        String key = String.format("%s.secret", botId);
        redisClient.put(key, secret);
        return true;
    }

    public String getSecret() {
        String key = String.format("%s.secret", botId);
        return redisClient.get(key);
    }

    boolean unsubscribe() {
        String key = String.format("%s.secret", botId);
        redisClient.delete(key);
        return true;
    }
}
