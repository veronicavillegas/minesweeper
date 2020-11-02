package minesweeper.service;

import net.spy.memcached.AddrUtil;
import net.spy.memcached.ConnectionFactoryBuilder;
import net.spy.memcached.MemcachedClient;
import net.spy.memcached.auth.AuthDescriptor;
import net.spy.memcached.auth.PlainCallbackHandler;

import java.io.IOException;

public class MemcachedHerokuService {
    public void save() {
        AuthDescriptor ad = new AuthDescriptor(
                new String[] { "PLAIN" },
                new PlainCallbackHandler(System.getenv("MEMCACHIER_USERNAME"),
                        System.getenv("MEMCACHIER_PASSWORD")));
        try {
            MemcachedClient mc = new MemcachedClient(
                    new ConnectionFactoryBuilder()
                            .setProtocol(ConnectionFactoryBuilder.Protocol.BINARY)
                            .setAuthDescriptor(ad).build(),
                    AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS")));

            mc.set("foo", 0, "bar");
            System.out.println(mc.get("foo"));
        } catch (IOException ioe) {
            System.err.println("Couldn't create a connection to MemCachier: \nIOException "
                    + ioe.getMessage());
        }
    }
}
