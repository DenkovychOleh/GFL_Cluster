package executor.service.services.handler;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.services.validator.ProxyValidationService;
import org.mockito.Mock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.util.Collection;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class ProxySourceQueueHandlerImplTest {

    @Mock
    private ProxyValidationService proxyValidator;

    private ProxySourceQueueHandlerImpl proxySourceQueueHandler;

    @BeforeEach
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        proxySourceQueueHandler = new ProxySourceQueueHandlerImpl(proxyValidator);
    }

    private ProxyConfigHolder createProxyConfigHolder() {
        ProxyNetworkConfig proxyNetworkConfig = new ProxyNetworkConfig("127.0.0.1", 8080);
        ProxyCredentials proxyCredentials = new ProxyCredentials("username", "password");
        return new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);
    }

    @Test
    public void testAddProxy() {
        ProxyConfigHolder proxyConfigHolder = createProxyConfigHolder();
        proxySourceQueueHandler.addProxy(proxyConfigHolder);
        assertEquals(1, proxySourceQueueHandler.size());
    }

    @Test
    public void testPollProxy() {
        ProxyConfigHolder proxyConfigHolder = createProxyConfigHolder();
        proxySourceQueueHandler.addProxy(proxyConfigHolder);
        Optional<ProxyConfigHolder> polledProxy = proxySourceQueueHandler.pollProxy();
        assertTrue(polledProxy.isPresent());
        assertEquals(proxyConfigHolder, polledProxy.get());
        assertEquals(0, proxySourceQueueHandler.size());
    }

    @Test
    public void testPollAllProxy() {
        ProxyConfigHolder proxyConfigHolder = createProxyConfigHolder();
        proxySourceQueueHandler.addProxy(proxyConfigHolder);
        Collection<ProxyConfigHolder> allProxies = proxySourceQueueHandler.pollAllProxy();
        assertEquals(1, allProxies.size());
        assertTrue(allProxies.contains(proxyConfigHolder));
        assertTrue(proxySourceQueueHandler.isEmpty());
    }

    @Test
    public void testSize() {
        ProxyConfigHolder proxyConfigHolder1 = createProxyConfigHolder();
        ProxyConfigHolder proxyConfigHolder2 = createProxyConfigHolder();
        proxySourceQueueHandler.addProxy(proxyConfigHolder1, proxyConfigHolder2);
        assertEquals(2, proxySourceQueueHandler.size());
    }

    @Test
    void testIsEmpty() {
        assertTrue(proxySourceQueueHandler.isEmpty());
        ProxyConfigHolder proxyConfigHolder = createProxyConfigHolder();
        proxySourceQueueHandler.addProxy(proxyConfigHolder);
        assertFalse(proxySourceQueueHandler.isEmpty());
    }

    @Test
    void testRemoveInvalidProxy() {
        ProxyConfigHolder validProxy = createProxyConfigHolder();

        ProxyNetworkConfig proxyNetworkConfig2 = new ProxyNetworkConfig("127.0.0.1", 8080);
        ProxyCredentials proxyCredentials2 = null;
        ProxyConfigHolder invalidProxy = new ProxyConfigHolder(proxyNetworkConfig2, proxyCredentials2);

        proxySourceQueueHandler.addProxy(validProxy, invalidProxy);
        proxySourceQueueHandler.removeInvalidProxy();
        assertEquals(1, proxySourceQueueHandler.size());
        assertEquals(validProxy, proxySourceQueueHandler.pollProxy().get());
    }
}