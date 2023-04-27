package executor.service.services.validator;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyNetworkConfig;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.concurrent.TimeUnit;

@Service
public class ProxyValidatorImpl implements ProxyValidator{
    @Override
    public boolean isValid(ProxyConfigHolder proxyConfigHolder) {
            if (proxyConfigHolder != null) {
                try {
                    ProxyNetworkConfig proxyNetworkConfig = proxyConfigHolder.getProxyNetworkConfig();
                    Proxy proxy = new Proxy(Proxy.Type.HTTP,
                            new InetSocketAddress(proxyNetworkConfig.getHostname(), proxyNetworkConfig.getPort()));
                    OkHttpClient client = new OkHttpClient.Builder()
                            .proxy(proxy)
                            .callTimeout(1, TimeUnit.SECONDS)
                            .build();

                } catch (IOException e) {
                    return false;
                }
            }
            return false;
    }
}
