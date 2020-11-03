package minesweeper.client;

import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.exception.MemcachedException;
import net.rubyeye.xmemcached.utils.AddrUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.concurrent.TimeoutException;

public enum MemcachedMinesweeperClient {
    INSTANCE;
    private final int PERSISTENCE_PERIOD = 86400; //24HS.

    public net.rubyeye.xmemcached.MemcachedClient getClient() {
        List<InetSocketAddress> servers =
                AddrUtil.getAddresses(System.getenv("MEMCACHIER_SERVERS").replace(",", " "));
        AuthInfo authInfo =
                AuthInfo.plain(System.getenv("MEMCACHIER_USERNAME"),
                        System.getenv("MEMCACHIER_PASSWORD"));

        MemcachedClientBuilder builder = new XMemcachedClientBuilder(servers);

        // Configure SASL auth for each server
        for(InetSocketAddress server : servers) {
            builder.addAuthInfo(server, authInfo);
        }

        // Use binary protocol
        builder.setCommandFactory(new BinaryCommandFactory());
        // Connection timeout in milliseconds (default: )
        builder.setConnectTimeout(1000);
        // Reconnect to servers (default: true)
        builder.setEnableHealSession(true);
        // Delay until reconnect attempt in milliseconds (default: 2000)
        builder.setHealSessionInterval(2000);
        net.rubyeye.xmemcached.MemcachedClient mc = null;
        try {
            mc = builder.build();
        } catch (IOException ioe) {
            System.err.println("Couldn't create a connection to MemCachier: " +
                    ioe.getMessage());
        }
        return mc;
    }

    public void save(String key, Object element) {
        net.rubyeye.xmemcached.MemcachedClient mc = getClient();
        String method = "save";

        try {
            mc.set(key, PERSISTENCE_PERIOD, element);
        } catch (TimeoutException te) {
            System.err.println("Timeout during " + method +
                    te.getMessage());
        } catch (InterruptedException ie) {
            System.err.println("Interrupt during " + method +
                    ie.getMessage());
        } catch (MemcachedException me) {
            System.err.println("Memcached error during " + method +
                    me.getMessage());
        }
    }

    public Object get(String key) {
        net.rubyeye.xmemcached.MemcachedClient mc = getClient();
        String method = "get";
        Object elementToReturn = null;
        try {
            elementToReturn = mc.get(key);
        } catch (TimeoutException te) {
            System.err.println("Timeout during " + method +
                    te.getMessage());
        } catch (InterruptedException ie) {
            System.err.println("Interrupt during " + method +
                    ie.getMessage());
        } catch (MemcachedException me) {
            System.err.println("Memcached error during " + method +
                    me.getMessage());
        }
        return elementToReturn;
    }

    public void update(String key, Object element){
        net.rubyeye.xmemcached.MemcachedClient mc = getClient();
        String method = "update";

        try {
            mc.replace(key, PERSISTENCE_PERIOD, element);
        } catch (TimeoutException te) {
            System.err.println("Timeout during " + method +
                    te.getMessage());
        } catch (InterruptedException ie) {
            System.err.println("Interrupt during " + method +
                    ie.getMessage());
        } catch (MemcachedException me) {
            System.err.println("Memcached error during " + method +
                    me.getMessage());
        }
    }
}
