package executor.service.services.step;

import executor.service.model.ProxyConfigHolder;

import java.io.IOException;
import java.util.ArrayList;

public interface ProxySources {
    ArrayList<ProxyConfigHolder> getProxy(String fileProxyCredentials,
                                          String fileProxyNetwork) throws IOException;
}
