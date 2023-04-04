package executor.service.services;

import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.services.step.StepExecutionClickCSS;
import executor.service.services.step.StepExecutionClickXpath;
import executor.service.services.step.StepExecutionSleep;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class ScenarioExecutorImpl implements ScenarioExecutor {
    private final StepExecutionClickCSS clickCSS;
    private final StepExecutionSleep sleep;
    private final StepExecutionClickXpath clickXpath;

    public ScenarioExecutorImpl(StepExecutionClickCSS clickCSS, StepExecutionSleep sleep, StepExecutionClickXpath clickXpath) {
        this.clickCSS = clickCSS;
        this.sleep = sleep;
        this.clickXpath = clickXpath;
    }

    @Override
    public void execute(Scenario scenario, WebDriver webDriver) {
        List<Step> steps = scenario.getSteps();
        String scenarioSite = scenario.getSite();
        webDriver.get(scenarioSite);
        for (Step step : steps) {
            String action = step.getAction();
            switch (action) {
                case "clickCSS":
                    clickCSS.step(webDriver, step);
                    break;
                case "sleep":
                    sleep.step(webDriver, step);
                    break;
                case "clickXpath":
                    clickXpath.step(webDriver, step);
                    break;
                default:
                    throw new RuntimeException();
            }
        }
    }
}