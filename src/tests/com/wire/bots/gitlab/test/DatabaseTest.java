package com.wire.bots.gitlab.test;

import com.wire.bots.gitlab.Database;
import com.wire.bots.sdk.Configuration;

import java.util.UUID;

public class DatabaseTest {

    //@Test
    public void test() throws Exception {
        Configuration.DB conf = new Configuration.DB();
        conf.host = "localhost";
        conf.port = 5432;
        conf.database = "postgres";
        conf.driver = "postgresql";
        conf.user = "dejankovacevic";
        conf.password = "password";

        String botId = UUID.randomUUID().toString();

        Database db = new Database(botId);
        String secret = "secret";

        boolean b = db.insertSecret(secret);
        assert b;

        String newSecret = db.getSecret();
        assert newSecret != null;
        assert newSecret.equals(secret);
    }
}
