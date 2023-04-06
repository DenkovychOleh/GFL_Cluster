package executor.service.services;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;

public class ExecutionServiceFacade implements ExecutionService {
    private final ScenarioExecutorImpl scenarioExecutor;
    private final ScenarioSourceListenerImpl scenarioSourceListener;
    private final WebDriverInitializer webDriverInitializer;

    public ExecutionServiceFacade(ScenarioExecutorImpl scenarioExecutor, ScenarioSourceListenerImpl scenarioSourceListener, WebDriverInitializer webDriverInitializer) {
        this.scenarioExecutor = scenarioExecutor;
        this.scenarioSourceListener = scenarioSourceListener;
        this.webDriverInitializer = webDriverInitializer;
    }

    @Override
    public void execute(Scenario scenario, ProxyConfigHolder proxyConfigHolder) {
        WebDriver webDriver = webDriverInitializer.create(proxyConfigHolder);
        scenarioSourceListener.execute();
        scenarioExecutor.execute(scenario,webDriver);
        webDriver.quit();
    }
}

