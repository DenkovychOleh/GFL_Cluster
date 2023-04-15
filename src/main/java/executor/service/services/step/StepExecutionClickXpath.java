package executor.service.services.step;

import executor.service.model.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

@Component
public class StepExecutionClickXpath implements StepExecution {

    @Override
    public String getStepAction() {
        return "clickXpath";
    }

    @Override
    public void step(WebDriver webDriver, Step step) {
        String xPath = step.getValue();
        WebElement element = webDriver.findElement(By.xpath(xPath));
        element.click();
    }

    public static StepExecution getInstance() {
        return new StepExecutionClickXpath();
    }
}
