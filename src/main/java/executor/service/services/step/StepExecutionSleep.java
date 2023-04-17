package executor.service.services.step;

import executor.service.model.Step;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;

@Component
public class StepExecutionSleep implements StepExecution{
    @Override
    public String getStepAction() {
        return "Sleep";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        try {
            int sleepTime = Integer.parseInt(step.getValue());
            Thread.sleep(sleepTime);
        }catch (NumberFormatException ex){
            throw new IllegalArgumentException(ex.getMessage());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
