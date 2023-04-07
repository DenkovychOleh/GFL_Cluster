package executor.service.services;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import static org.mockito.Mockito.*;

class ExecutionServiceFacadeTest {

    private ExecutionServiceFacade executionServiceFacade;
    private ScenarioExecutorImpl scenarioExecutor;
    private ScenarioSourceListenerImpl scenarioSourceListener;
    private WebDriverInitializer webDriverInitializer;

    @BeforeEach
    public void setUp() {
        scenarioExecutor = mock(ScenarioExecutorImpl.class);
        scenarioSourceListener = mock(ScenarioSourceListenerImpl.class);
        webDriverInitializer = mock(WebDriverInitializer.class);
        executionServiceFacade = new ExecutionServiceFacade(scenarioExecutor, scenarioSourceListener, webDriverInitializer);
    }

    @Test
    public void testExecute() {
        Scenario scenario = mock(Scenario.class);
        ProxyConfigHolder proxyConfigHolder = mock(ProxyConfigHolder.class);
        WebDriver webDriver = mock(WebDriver.class);

        when(webDriverInitializer.create(proxyConfigHolder)).thenReturn(webDriver);

        executionServiceFacade.execute(scenario, proxyConfigHolder);

        verify(webDriverInitializer).create(proxyConfigHolder);
        verify(scenarioSourceListener).execute();
        verify(scenarioExecutor).execute(scenario, webDriver);
        verify(webDriver).quit();
    }
}