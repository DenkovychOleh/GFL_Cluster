package executor.service.services;

import executor.service.config.PropertiesLoader;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.apache.commons.configuration2.Configuration;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class ChromeDriverInitializer implements WebDriverInitializer {

    private static final Configuration CONFIGURATION;

    static {
        CONFIGURATION = PropertiesLoader.getInstance().getConfiguration();
        System.setProperty("webdriver.chrome.driver", CONFIGURATION.getString("webdriver.config.executable"));
    }

    @Override
    public WebDriver create(ProxyConfigHolder ProxyConfigHolder) {
        ChromeOptions options = createOptions(ProxyConfigHolder);
        return new ChromeDriver(options);
    }

    private ChromeOptions createOptions(ProxyConfigHolder proxyConfigHolder) {
        ProxyCredentials proxyCredentials;
        ProxyNetworkConfig proxyNetworkConfig;
        ChromeOptions options = new ChromeOptions();
        if (proxyConfigHolder != null) {
            proxyCredentials = proxyConfigHolder.getProxyCredentials();
            proxyNetworkConfig = proxyConfigHolder.getProxyNetworkConfig();
            if (proxyCredentials != null && proxyNetworkConfig != null) {
                options.addArguments(
                        String.format("--proxy-server=%s:%s@%s:%d",
                                proxyCredentials.getUsername(),
                                proxyCredentials.getPassword(),
                                proxyNetworkConfig.getHostname(),
                                proxyNetworkConfig.getPort())
                );
            }
        }
        String userAgent = CONFIGURATION.getString("webdriver.config.userAgent");
        long pageLoadTimeout = CONFIGURATION.getLong("webdriver.config.pageLoadTimeout");
        long implicitlyWait = CONFIGURATION.getLong("webdriver.config.implicitlyWait");
        options.addArguments(String.format("--user-agent=%s", userAgent), "--remote-allow-origins=*");
        options.setPageLoadTimeout(Duration.ofMillis(pageLoadTimeout));
        options.setImplicitWaitTimeout(Duration.ofMillis(implicitlyWait));
        return options;
    }
}
