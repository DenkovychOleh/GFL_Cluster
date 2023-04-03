package executor.service.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ProxyNetworkConfigTest {
    private final String hostname = "hostname";
    private final Integer port = 8080;
    private ProxyNetworkConfig actualProxyNetworkConfig;
    private ProxyNetworkConfig emptyProxyNetworkConfig;


    @BeforeEach
    public void setUp() {
        actualProxyNetworkConfig = new ProxyNetworkConfig(hostname, port);
        emptyProxyNetworkConfig = new ProxyNetworkConfig();
    }

    @Test
    public void testGetters() {
        Assertions.assertEquals("hostname", actualProxyNetworkConfig.getHostname());
        Assertions.assertEquals(8080, actualProxyNetworkConfig.getPort());
    }

    @Test
    public void testSetters() {
        emptyProxyNetworkConfig.setHostname("localhost");
        emptyProxyNetworkConfig.setPort(5151);
        Assertions.assertEquals("localhost", emptyProxyNetworkConfig.getHostname());
        Assertions.assertEquals(5151, emptyProxyNetworkConfig.getPort());
    }

    @Test
    public void testEquals() {
        emptyProxyNetworkConfig.setHostname("hostname");
        emptyProxyNetworkConfig.setPort(8080);

        Assertions.assertEquals(emptyProxyNetworkConfig, actualProxyNetworkConfig);
    }
    @Test
    public void testNotEquals(){
        emptyProxyNetworkConfig.setHostname("hostname1");
        emptyProxyNetworkConfig.setPort(5555);

        Assertions.assertNotEquals(emptyProxyNetworkConfig, actualProxyNetworkConfig);

    }

    @Test
    public void testHashCode() {
        ProxyNetworkConfig expectedProxyNetworkConfig = new ProxyNetworkConfig("hostname", 8080);
        Assertions.assertEquals(expectedProxyNetworkConfig.hashCode(), actualProxyNetworkConfig.hashCode());
    }

    @Test
    public void testProxyNetworkConfigNotNull(){
        ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig("localhost", 5050);
        Assertions.assertNotNull(actualProxyNetworkConfig);
        Assertions.assertNotNull(proxyNetworkConfig);
    }

    @Test
    public void testNullValues(){
        Assertions.assertNull(emptyProxyNetworkConfig.getHostname());
        Assertions.assertNull(emptyProxyNetworkConfig.getPort());}
}