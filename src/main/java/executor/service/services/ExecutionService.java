package executor.service.services;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;

public interface ExecutionService {
    void execute(Scenario scenario, ProxyConfigHolder proxyConfigHolder) ;
}
