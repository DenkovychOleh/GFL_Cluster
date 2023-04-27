package executor.service.services.validator;

import executor.service.model.ProxyNetworkConfig;
import org.springframework.stereotype.Service;

import javax.xml.ws.WebServiceException;
import java.io.IOException;
import java.net.*;
import java.util.List;

@Service
public class ProxyValidationService {
    private int proxyValidate(ProxyNetworkConfig proxyConfig) {
        try {
            URL weburl = new URL("http://info.cern.ch/");
            Proxy webProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyConfig.getHostname(), proxyConfig.getPort()));
            HttpURLConnection webProxyConnection = (HttpURLConnection) weburl.openConnection(webProxy);
            return webProxyConnection.getResponseCode();
        } catch (IOException e) {
            return 0;
        }
    }

    public ProxyNetworkConfig getValidProxy(List<ProxyNetworkConfig> proxyList) {
        return proxyList.stream()
                .filter(proxy -> proxyValidate(proxy) == 200)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException("No one proxy is valid."));
    }
}
