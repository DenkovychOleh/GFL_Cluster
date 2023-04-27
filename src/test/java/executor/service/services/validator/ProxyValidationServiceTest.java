package executor.service.services.validator;

import executor.service.model.ProxyNetworkConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProxyValidationServiceTest {
    private List<ProxyNetworkConfig> proxyList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        proxyList.add(new ProxyNetworkConfig("107.151.192.29", 45787));
        proxyList.add(new ProxyNetworkConfig("103.122.179.43", 45787));
        proxyList.add(new ProxyNetworkConfig("221.145.195.200", 80));
        proxyList.add(new ProxyNetworkConfig("175.9.237.225", 8964));
        proxyList.add(new ProxyNetworkConfig("223.100.178.167", 9091));
        proxyList.add(new ProxyNetworkConfig("65.108.69.40", 10001));
        proxyList.add(new ProxyNetworkConfig("183.221.221.149", 9091));
        proxyList.add(new ProxyNetworkConfig("123.60.172.164", 7890));
        proxyList.add(new ProxyNetworkConfig("203.19.38.114", 1080));
        proxyList.add(new ProxyNetworkConfig("1.4.250.111", 8080));

    }

    @Test
    void validateProxy() {
        ProxyValidationService proxyValidator = new ProxyValidationService();
        ProxyNetworkConfig proxy = proxyValidator.getValidProxy(proxyList);
        System.out.println(proxy.getHostname() + " : " + proxy.getPort());
        assertNotNull(proxyValidator.getValidProxy(proxyList));
    }
}