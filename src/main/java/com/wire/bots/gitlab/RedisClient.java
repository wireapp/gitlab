package com.wire.bots.gitlab;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.time.Duration;

class RedisClient {
    private static JedisPool pool;
    private final String host;
    private final Integer port;
    private final String password;
    private static int timeout;

    RedisClient(String host, Integer port, String password, int timeout) {
        this.host = host;
        this.port = port;
        this.password = password;
        RedisClient.timeout = timeout;
    }

    private static JedisPoolConfig buildPoolConfig() {
        final JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxTotal(1100);
        poolConfig.setMaxIdle(16);
        poolConfig.setMinIdle(16);
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(Duration.ofSeconds(60).toMillis());
        poolConfig.setTimeBetweenEvictionRunsMillis(Duration.ofSeconds(30).toMillis());
        poolConfig.setNumTestsPerEvictionRun(3);
        poolConfig.setBlockWhenExhausted(true);
        return poolConfig;
    }

    private static JedisPool pool(String host, Integer port, String password) {
        if (pool == null) {
            JedisPoolConfig poolConfig = buildPoolConfig();
            if (password != null && port != null)
                pool = new JedisPool(poolConfig, host, port, timeout, password);
            else if (port != null)
                pool = new JedisPool(poolConfig, host, port, timeout);
            else
                pool = new JedisPool(poolConfig, host);
        }
        return pool;
    }

    String get(String id) {
        try (Jedis jedis = getConnection()) {
            String key = String.format("gitlab_%s", id);
            return jedis.get(key);
        }
    }

    void put(String id, String data) {
        try (Jedis jedis = getConnection()) {
            String key = String.format("gitlab_%s", id);
            jedis.set(key, data);
        }
    }

    void delete(String id) {
        try (Jedis jedis = getConnection()) {
            String key = String.format("gitlab_%s", id);
            jedis.del(key);
        }
    }

    private Jedis getConnection() {
        return pool(host, port, password).getResource();
    }

}

