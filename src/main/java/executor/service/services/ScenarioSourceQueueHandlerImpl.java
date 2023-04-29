package executor.service.services;

import executor.service.model.Scenario;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class ScenarioSourceQueueHandlerImpl implements ScenarioSourceQueueHandler{

    private final Queue<Scenario> scenarioQueue;
    public ScenarioSourceQueueHandlerImpl() {
        scenarioQueue = new LinkedBlockingQueue<>();
    }

    @Override
    public Scenario poll() {
        return scenarioQueue.poll();
    }

    @Override
    public void offer(Scenario scenario) {
        scenarioQueue.offer(scenario);
    }

    @Override
    public boolean isEmpty() {
        return scenarioQueue.isEmpty();
    }
}
