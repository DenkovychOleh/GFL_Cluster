package executor.service.services;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.services.step.StepExecutionClickCSS;
import executor.service.services.step.StepExecutionClickXpath;
import executor.service.services.step.StepExecutionSleep;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
class ScenarioExecutorServiceTest {
    @Mock
    private WebDriver webDriver;
    @Mock
    private StepExecutionClickCSS stepExecutionClickCSS;
    @Mock
    private StepExecutionSleep stepExecutionSleep;
    @Mock
    private StepExecutionClickXpath stepExecutionClickXpath;
    private ScenarioExecutor scenarioExecutor;
    private Step step_1;
    private Step step_2;
    private Step step_3;
    private List<Step> stepList;
    private Scenario scenario;

    @BeforeEach
    public void before() {

        step_1 = new Step("clickCSS", "body > ul > li > a");
        step_2 = new Step("sleep", "5000");
        step_3 = new Step("clickXpath", "/html/body/style");
        stepList = new ArrayList<>(Arrays.asList(step_3, step_1, step_2));
        scenario = new Scenario("SomeName", "https://www.google.com.ua/", stepList);
        scenarioExecutor = new ScenarioExecutorImpl(stepExecutionClickCSS, stepExecutionSleep, stepExecutionClickXpath);
    }


    @Test
    public void testForTryingExecutionClickCss() {
        scenarioExecutor.execute(scenario, webDriver);
        Mockito.verify(stepExecutionClickCSS).step(webDriver, step_1);
    }

    @Test
    public void testForTryingExecutionSleep() {
        scenarioExecutor.execute(scenario, webDriver);
        Mockito.verify(stepExecutionSleep).step(webDriver, step_2);
    }

    @Test
    public void testForTryingExecutionClickXpath() {
        scenarioExecutor.execute(scenario, webDriver);
        Mockito.verify(stepExecutionClickXpath).step(webDriver, step_3);
    }


    @Test
    public void throwExceptionIfSleepActionDoesntCorrect() {
        step_1 = new Step("unknown", "5000");
        List<Step> listSteps = new ArrayList<>(Arrays.asList(step_1, step_2, step_3));
        Scenario scenarioDTO = new Scenario("SomeName", "https://www.google.com.ua/", listSteps);
        assertThrows(RuntimeException.class, () -> scenarioExecutor.execute(scenarioDTO, webDriver));
    }

}