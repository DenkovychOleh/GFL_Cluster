package executor.service.services.handler;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyNetworkConfig;
import executor.service.services.validator.ProxyValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.LinkedBlockingQueue;

@PropertySource("classpath:schedule.properties")
@Service
public class ProxySourceQueueHandlerImpl implements ProxySourceQueueHandler {

    private final Queue<ProxyConfigHolder> proxyQueue = new LinkedBlockingQueue<>();
    private final ProxyValidator proxyValidator;

    @Value("${fixedRate}")
    private String fixedRate;

    public ProxySourceQueueHandlerImpl(ProxyValidator proxyValidator) {
        this.proxyValidator = proxyValidator;
    }


    @Override
    public void addProxy(ProxyConfigHolder... proxyConfigHolder) {
        Collections.addAll(proxyQueue, proxyConfigHolder);
    }

    @Override
    public Optional<ProxyConfigHolder> pollProxy() {
        return Optional.ofNullable(proxyQueue.poll());
    }

    @Override
    public Collection<ProxyConfigHolder> pollAllProxy() {
        HashSet<ProxyConfigHolder> result = new HashSet<>(proxyQueue);
        proxyQueue.clear();
        return result;
    }

    @Override
    public int size() {
        return proxyQueue.size();
    }

    @Override
    public boolean isEmpty() {
        return proxyQueue.isEmpty();
    }

    @Scheduled(fixedRateString = "${fixedRate}")
    @Override
    public void removeInvalidProxy() {
        if (!isEmpty()) {
            proxyQueue.removeIf(proxyConfigHolder -> {
                boolean proxyValid = proxyValidator.isValid(proxyConfigHolder);
                boolean networkConfig = isValidNetworkConfig(proxyConfigHolder.getProxyNetworkConfig());
                return !proxyValid || !networkConfig;
            });
        }
    }


    private boolean isValidNetworkConfig(ProxyNetworkConfig networkConfig) {
        return networkConfig != null &&
                networkConfig.getHostname() != null &&
                networkConfig.getPort() != null;
    }
}
