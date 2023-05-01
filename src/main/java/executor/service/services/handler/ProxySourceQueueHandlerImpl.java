package executor.service.services.handler;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import executor.service.services.validator.ProxyValidationService;
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
    private final ProxyValidationService proxyValidator;

    @Value("${fixedRate}")
    private String fixedRate;

    public ProxySourceQueueHandlerImpl(ProxyValidationService proxyValidator) {
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
                ProxyNetworkConfig networkConfig = proxyConfigHolder.getProxyNetworkConfig();
                ProxyCredentials credentials = proxyConfigHolder.getProxyCredentials();
                return networkConfig == null ||
                        networkConfig.getHostname() == null ||
                        networkConfig.getPort() == null ||
                        credentials == null ||
                        proxyValidator.proxyValidate(networkConfig);
            });
        }
    }
}
