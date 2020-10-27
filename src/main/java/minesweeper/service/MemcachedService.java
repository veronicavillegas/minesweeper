package minesweeper.service;

import net.spy.memcached.MemcachedClient;

import java.io.IOException;
import java.net.InetSocketAddress;

public enum MemcachedService {
    INSTANCE;
    public final int EXPIRATION_TIME_SECONDS = 86400; //24HS
    public final String HOST = "127.0.0.1";
    public final int PORT = 11211;

    public void save(String key, Object object) throws IOException {
        getConnection().set(key, EXPIRATION_TIME_SECONDS, object);
    }

    public Object get(String key) throws IOException {
        return getConnection().get(key);
    }

    public  void update(String key, Object object) throws IOException {
        getConnection().replace(key, EXPIRATION_TIME_SECONDS, object);
    }

    private MemcachedClient getConnection () throws IOException {
        MemcachedClient mcc = new MemcachedClient(new InetSocketAddress(HOST, PORT));
        return mcc;
    }
}
