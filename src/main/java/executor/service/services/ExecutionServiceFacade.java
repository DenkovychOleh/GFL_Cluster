package executor.service.services;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Service;

@Service
public class ExecutionServiceFacade implements ExecutionService {
    private final ScenarioExecutor scenarioExecutor;
    private final ScenarioSourceListener scenarioSourceListener;
    private final WebDriverInitializer webDriverInitializer;

    public ExecutionServiceFacade(ScenarioExecutor scenarioExecutor, ScenarioSourceListener scenarioSourceListener, WebDriverInitializer webDriverInitializer) {
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
