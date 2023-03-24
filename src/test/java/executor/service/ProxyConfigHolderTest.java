package executor.service;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ProxyConfigHolderTest {
    private final String hostname = "hostname";
    private final Integer port = 1234;

    private final String userName = "username";
    private final String password = "password";


    private final ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig(hostname, port);
    private final ProxyCredentials proxyCredentials = new ProxyCredentials(userName, password);

    private ProxyConfigHolder actualProxyConfigHolder;
    private ProxyConfigHolder emptyProxyConfigHolder;

    private ProxyConfigHolder expectedProxyConfigHolder;

    @BeforeEach
    public void setUp() {
        actualProxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);
        emptyProxyConfigHolder = new ProxyConfigHolder();
        expectedProxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig,proxyCredentials);
    }

    @Test
    public void notNull() {
        Assertions.assertNotNull(actualProxyConfigHolder);
    }

    @Test
    public void testObjectsAreEqual() {
        Assertions.assertEquals(expectedProxyConfigHolder, actualProxyConfigHolder);
    }

    @Test
    public void testObjectsNotEqual() {
        Assertions.assertNotEquals(emptyProxyConfigHolder, actualProxyConfigHolder);
    }

    @Test
    public void testConstructor() {
        String expectedHostNameNetworkConf = "hostname";
        Integer expectedPortNetworkConf = 1234;

        String expectedUserNameCredentials = "username";
        String expectedPasswordCredentials = "password";

        String actualHostName = proxyNetworkConfig.getHostname();
        Integer actualPort = proxyNetworkConfig.getPort();

        String actualUserName = proxyCredentials.getUsername();
        String actualPassword = proxyCredentials.getPassword();

        Assertions.assertEquals(expectedHostNameNetworkConf, actualHostName);
        Assertions.assertEquals(expectedPortNetworkConf, actualPort);

        Assertions.assertEquals(expectedUserNameCredentials, actualUserName);
        Assertions.assertEquals(expectedPasswordCredentials, actualPassword);
    }

    @Test
    public void testEmptyConstructor(){
        Assertions.assertNull(emptyProxyConfigHolder.getProxyNetworkConfig());
        Assertions.assertNull(emptyProxyConfigHolder.getProxyCredentials());
    }

    @Test
    public void testSettersAndGetters() {
        emptyProxyConfigHolder.setProxyNetworkConfig(proxyNetworkConfig);
        emptyProxyConfigHolder.setProxyCredentials(proxyCredentials);

        Assertions.assertEquals(proxyNetworkConfig, emptyProxyConfigHolder.getProxyNetworkConfig());
        Assertions.assertEquals(proxyCredentials, emptyProxyConfigHolder.getProxyCredentials());
    }

    @Test
    public void testHashCodeEquals() {
        Assertions.assertEquals(expectedProxyConfigHolder.hashCode(), actualProxyConfigHolder.hashCode());
    }

    @Test
    public void testHashCodeNotEquals(){
        Assertions.assertNotEquals(expectedProxyConfigHolder.hashCode(), emptyProxyConfigHolder.hashCode());
    }

    @Test
    public void testNetworkConfigNull() {
        emptyProxyConfigHolder.setProxyNetworkConfig(null);
        Assertions.assertNull(emptyProxyConfigHolder.getProxyNetworkConfig());
    }

    @Test
    public void testCredentialsNull() {
        emptyProxyConfigHolder.setProxyCredentials(null);
        Assertions.assertNull(emptyProxyConfigHolder.getProxyCredentials());
    }
}