package executor.service.services;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.apache.commons.configuration2.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

public class ChromeDriverInitializerTest {

    private WebDriver driver;
    private ProxyConfigHolder proxyConfigHolder;
    private ProxyConfigHolder emptyProxyConfigHolder;
    private ProxyNetworkConfig proxyNetworkConfig;
    private ProxyCredentials proxyCredentials;
    private Properties properties;

    @BeforeEach
    public void setUp() {
        emptyProxyConfigHolder = new ProxyConfigHolder();
        proxyNetworkConfig = new ProxyNetworkConfig("host", 8080);
        proxyCredentials = new ProxyCredentials("user", "pass");
        proxyConfigHolder = new ProxyConfigHolder(proxyNetworkConfig, proxyCredentials);
        try (InputStream input = ChromeDriverInitializer.class.getClassLoader().getResourceAsStream("webDriver.properties")) {
            properties = new Properties();
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testChromeDriverExecutableIsSet() {
        ChromeDriverInitializer driverInitializer = new ChromeDriverInitializer();
        driver = driverInitializer.create(proxyConfigHolder);
        assertEquals(System.getProperty("webdriver.chrome.driver"), properties.getProperty("webdriver.config.executable"));
        driver.quit();
    }

    @Test
    public void testCreateMethodReturnsChromeDriverInstance() {
        ChromeDriverInitializer driverInitializer = new ChromeDriverInitializer();
        driver = driverInitializer.create(proxyConfigHolder);
        assertTrue(driver instanceof ChromeDriver);
        driver.quit();
    }

    @Test
    public void testDriverNotNull() {
        ChromeDriverInitializer initializer = new ChromeDriverInitializer();
        driver = initializer.create(emptyProxyConfigHolder);
        assertNotNull(driver);
        driver.quit();
    }

    @Test
    public void testCreateOptionsWithProxyConfigHolder() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Pattern pattern;
        Matcher matcher;
        ChromeOptions options;
        String regex = "--proxy-server=[^,}]+";
        String expected = String.format("--proxy-server=%s:%s@%s:%d",
                proxyCredentials.getUsername(),
                proxyCredentials.getPassword(),
                proxyNetworkConfig.getHostname(),
                proxyNetworkConfig.getPort());
        ChromeDriverInitializer chromeDriverInitializer = new ChromeDriverInitializer();
        Method createOptions = ChromeDriverInitializer.class.getDeclaredMethod("createOptions", ProxyConfigHolder.class);
        createOptions.setAccessible(true);
        options = (ChromeOptions) createOptions.invoke(chromeDriverInitializer, proxyConfigHolder);
        String optionsObject = options.getCapability("goog:chromeOptions").toString();
        pattern = Pattern.compile(regex);
        matcher = pattern.matcher(optionsObject);
        String result = "";
        if (matcher.find()) {
            result = matcher.group();
        }
        assertEquals(expected, result);
    }


    @Test
    public void testConfigurationPropertiesAreReadCorrectly() throws NoSuchFieldException, IllegalAccessException {
        Field field = ChromeDriverInitializer.class.getDeclaredField("CONFIGURATION");
        field.setAccessible(true);
        ChromeDriverInitializer driverInitializer = new ChromeDriverInitializer();
        Configuration configuration = (Configuration) field.get(driverInitializer);
        assertEquals(configuration.getString("webdriver.config.executable"), properties.getProperty("webdriver.config.executable"));
        assertEquals(configuration.getString("webdriver.config.userAgent"), properties.getProperty("webdriver.config.userAgent"));
        assertEquals(configuration.getLong("webdriver.config.pageLoadTimeout"), Long.parseLong(properties.getProperty("webdriver.config.pageLoadTimeout")));
        assertEquals(configuration.getLong("webdriver.config.implicitlyWait"), Long.parseLong(properties.getProperty("webdriver.config.implicitlyWait")));
    }
}
