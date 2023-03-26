package executor.service.services.step;

import executor.service.model.Step;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledIf;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class StepExecutionClickXpathTest {

    private final String headerXpath = "//*[@id=\"header-blocks--2\"]/div[1]/div/div[3]/div/div/h3/span[1]";
    private final String wrongHeaderXpath = "//*[@id=\"header-blocks--2\"]/div[1]/div/div[3]/div/div/h3/span[2]";
    private final File file = new File("src/main/resources/chromedriver.exe");
    private StepExecutionClickXpath stepExecutionClickXpath;
    private Step step;
    private Step notInitializedStep;
    private WebDriver webDriver;
    private WebDriver notInitializedWebDriver;
    private static final boolean isActiveDriver = false;

    static {
        System.setProperty("webdriver.chrome.driver", "src/main/resources/chromedriver.exe");
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--headless=new");
        if (file.exists()) {
            webDriver = new ChromeDriver(options);
        }
        step = new Step("clickXpath", "/html/body/ul/li[4]/a");
        stepExecutionClickXpath = new StepExecutionClickXpath();
    }

    @AfterEach
    void tearDown() {
        webDriver.quit();
    }

    @Test
    void testGetStepAction() {
        String actual = stepExecutionClickXpath.getStepAction();
        assertEquals(step.getAction(), actual);
    }

    @Test
    @DisabledIf("isActiveDriver")
    void testStepSuccess() {
        webDriver.get("http://info.cern.ch/");

        String xPath = step.getValue();
        WebElement element = webDriver.findElement(By.xpath(xPath));
        element.click();

        WebElement headerElement = webDriver.findElement(By.xpath(headerXpath));
        String header = headerElement.getText();
        assertEquals("About CERN", header);
    }

    @Test
    @DisabledIf("isActiveDriver")
    void testStepNotEquals() {
        webDriver.get("http://info.cern.ch/");

        String xPath = step.getValue();
        WebElement element = webDriver.findElement(By.xpath(xPath));
        element.click();

        WebElement headerElement = webDriver.findElement(By.xpath(wrongHeaderXpath));
        String header = headerElement.getText();
        assertNotEquals("About CERN", header);
    }

    @Test
    @DisabledIf("isActiveDriver")
    void testStepTimeOut() {
        assertTimeout(Duration.ofSeconds(10), () -> {
        webDriver.get("http://info.cern.ch/");

        String xPath = step.getValue();
        WebElement element = webDriver.findElement(By.xpath(xPath));
        element.click();
        });
    }

    @Test
    @DisabledIf("isActiveDriver")
    void testStepNullPointerExceptionByStep() {
        assertThrows(NullPointerException.class, () -> {
            String xPath = notInitializedStep.getValue();
            WebElement element = webDriver.findElement(By.xpath(xPath));
            element.click();
        });
    }

    @Test
    void testStepNullPointerExceptionByWebDriver() {
        assertThrows(NullPointerException.class, () -> {
            String xPath = step.getValue();
            WebElement element = notInitializedWebDriver.findElement(By.xpath(xPath));
            element.click();
        });
    }
}