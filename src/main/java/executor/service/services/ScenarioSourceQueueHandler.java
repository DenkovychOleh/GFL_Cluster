package executor.service.services;

import executor.service.model.Scenario;

public interface ScenarioSourceQueueHandler {
    Scenario poll();
    void offer(Scenario scenario);
    boolean isEmpty();
}
