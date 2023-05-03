package executor.service.services;

import executor.service.model.ProxyConfigHolder;

import java.io.IOException;
import java.util.List;

public interface ProxySourcesClient {
    ProxyConfigHolder getProxy();
    List<ProxyConfigHolder> getProxy(String fileProxyCredentials,
                                     String fileProxyNetwork) throws IOException;
}
