package executor.service.services.validator;

import executor.service.model.ProxyConfigHolder;

public interface ProxyValidator {
    boolean isValid(ProxyConfigHolder proxyConfigHolder);
}
