package executor.service.services.validator;

import executor.service.model.ProxyNetworkConfig;
import org.springframework.stereotype.Service;
import java.io.IOException;
import java.net.*;
@Service
public class ProxyValidationService {
    public boolean proxyValidate(ProxyNetworkConfig proxyConfig) {
        if (proxyConfig != null) {
            try {
                URL weburl = new URL("http://info.cern.ch/");
                Proxy webProxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyConfig.getHostname(), proxyConfig.getPort()));
                HttpURLConnection webProxyConnection = (HttpURLConnection) weburl.openConnection(webProxy);
                if (webProxyConnection.getResponseCode() == 200) {
                    return true;
                }
            } catch (IOException e) {
                return false;
            }
        }
        return false;
    }
}