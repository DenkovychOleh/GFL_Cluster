package executor.service.services.handler;

import executor.service.model.ProxyConfigHolder;

import java.util.Collection;
import java.util.Optional;

public interface ProxySourceQueueHandler {
    void addProxy(ProxyConfigHolder... proxyConfigHolder);

    Optional<ProxyConfigHolder> pollProxy();

    Collection<ProxyConfigHolder> pollAllProxy();

    int size();

    boolean isEmpty();

    void removeInvalidProxy();
}
