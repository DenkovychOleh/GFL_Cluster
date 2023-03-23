package executor.services;

import executor.service.services.ChromeDriverInitializer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WebDriverInitializerTest {

    private WebDriver driver;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testDriverNotNull() {
        ChromeDriverInitializer initializer = new ChromeDriverInitializer();
        driver = initializer.create(null);
        assertNotNull(driver);
        assertTrue(driver instanceof ChromeDriver);
    }

    @AfterEach
    public void tearDown() {
        driver.quit();
    }
}
