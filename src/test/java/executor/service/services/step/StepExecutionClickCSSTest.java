package executor.service.services.step;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Step;
import executor.service.services.ChromeDriverInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.junitpioneer.jupiter.SetSystemProperty;
import org.mockito.Mockito;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class StepExecutionClickCSSTest {

    private WebDriver webDriver;
    private Step step;
    private StepExecutionClickCSS clickCSS;
    private final File file = new File("src/main/resources/chromedriver.exe");

    @BeforeEach
    @SetSystemProperty(key = "webdriver.chrome.driver", value = "src/main/resources/chromedriver.exe")
    public void setUp() {
        if (file.exists()) {
            webDriver = new ChromeDriverInitializer().create(new ProxyConfigHolder());
        }
        step = new Step("clickCSS", "body > ul > li:nth-child(1) > a");
        clickCSS = new StepExecutionClickCSS();
    }

    @AfterEach
    public void tearDown() {
        if (webDriver != null) {
            webDriver.quit();
        }
    }

    @Test
    public void testGetAction() {
        String actual = clickCSS.getStepAction();
        assertEquals(step.getAction(), actual);
    }

    @Test
    public void testStepWasExecuted() {
        WebDriver mockDriver = Mockito.mock(WebDriver.class);
        WebElement mockElement = Mockito.mock(WebElement.class);
        when(mockDriver.findElement(any(By.class))).thenReturn(mockElement);
        clickCSS.step(mockDriver, step);
        Mockito.verify(mockElement).click();
    }

    @Test
    @DisabledIf("isActiveDriver")
    public void testStepInteractWithCorrectElement() {
        webDriver.get("http://info.cern.ch");
        clickCSS.step(webDriver, step);
        WebElement h1 = webDriver.findElement(By.cssSelector("body > h1"));
        assertEquals("World Wide Web", h1.getText());
    }

    private boolean isActiveDriver() {
        return !file.exists();
    }

}