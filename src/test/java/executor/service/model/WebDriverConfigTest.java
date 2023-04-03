package executor.service.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WebDriverConfigTest {
    private final String webDriverExecutable = "WebDriver";
    private final String userAgent = "User agent";
    private final Long pageLoadTimeout = 10L;
    private final Long implicitlyWait = 15L;

    private WebDriverConfig actualWebDriverConfig;
    private WebDriverConfig emptyWebDriverConfig;

    @BeforeEach
    public void setUp() {
        actualWebDriverConfig = new WebDriverConfig(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);
        emptyWebDriverConfig = new WebDriverConfig();
    }

    @Test
    public void testNotNull() {
        Assertions.assertNotNull(actualWebDriverConfig);
        Assertions.assertNotNull(emptyWebDriverConfig);
    }

    @Test
    public void testEqualsObjects() {
        WebDriverConfig expectedCredentials = new WebDriverConfig(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);
        Assertions.assertEquals(expectedCredentials, actualWebDriverConfig);
    }

    @Test
    public void testNotEqualsObjects() {
        WebDriverConfig newCredentials = new WebDriverConfig("NotWebDriver", "Agent", 6L, 12L);
        Assertions.assertNotEquals(newCredentials, actualWebDriverConfig);
    }

    @Test
    public void testConstructor() {
        Assertions.assertEquals(webDriverExecutable, actualWebDriverConfig.getWebDriverExecutable());
        Assertions.assertEquals(userAgent, actualWebDriverConfig.getUserAgent());
        Assertions.assertEquals(pageLoadTimeout, actualWebDriverConfig.getPageLoadTimeout());
        Assertions.assertEquals(implicitlyWait, actualWebDriverConfig.getImplicitlyWait());
    }

    @Test
    public void testEmptyConstructor() {
        Assertions.assertNull(emptyWebDriverConfig.getWebDriverExecutable());
        Assertions.assertNull(emptyWebDriverConfig.getUserAgent());
        Assertions.assertNull(emptyWebDriverConfig.getPageLoadTimeout());
        Assertions.assertNull(emptyWebDriverConfig.getImplicitlyWait());
    }

    @Test
    public void testSettersAndGetters() {
        emptyWebDriverConfig.setWebDriverExecutable(webDriverExecutable);
        emptyWebDriverConfig.setUserAgent(userAgent);
        emptyWebDriverConfig.setPageLoadTimeout(pageLoadTimeout);
        emptyWebDriverConfig.setImplicitlyWait(implicitlyWait);
        Assertions.assertEquals(webDriverExecutable, emptyWebDriverConfig.getWebDriverExecutable());
        Assertions.assertEquals(userAgent, emptyWebDriverConfig.getUserAgent());
        Assertions.assertEquals(pageLoadTimeout, emptyWebDriverConfig.getPageLoadTimeout());
        Assertions.assertEquals(implicitlyWait, emptyWebDriverConfig.getImplicitlyWait());
    }

    @Test
    public void testHashCode() {
        WebDriverConfig expectedCredentials = new WebDriverConfig(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);
        Assertions.assertEquals(expectedCredentials.hashCode(), actualWebDriverConfig.hashCode());
    }

    @Test
    public void testSetWebDriverExecutableForNull() {
        actualWebDriverConfig.setWebDriverExecutable(null);
        Assertions.assertNull(actualWebDriverConfig.getWebDriverExecutable());
    }

    @Test
    public void testSetUserAgentForNull() {
//        emptyWebDriverConfig.setUserAgent(null);
        Assertions.assertNull(emptyWebDriverConfig.getUserAgent());
    }

    @Test
    public void testSetPageLoadTimeoutForNull() {
        actualWebDriverConfig.setPageLoadTimeout(null);
        Assertions.assertNull(actualWebDriverConfig.getPageLoadTimeout());
    }

    @Test
    public void testSetImplicitlyWaitForNull() {
        actualWebDriverConfig.setImplicitlyWait(null);
        Assertions.assertNull(actualWebDriverConfig.getImplicitlyWait());
    }

    @Test
    public void testToStringForNullValues() {
        String expectedToString = "WebDriverConfig{webDriverExecutable='null', userAgent='null', pageLoadTimeout=null, implicitlyWait=null}";
        Assertions.assertEquals(expectedToString, emptyWebDriverConfig.toString());
    }

    @Test
    public void testToString() {
        String expectedToString = "WebDriverConfig{webDriverExecutable='WebDriver', userAgent='User agent', pageLoadTimeout=10, implicitlyWait=15}";
        Assertions.assertEquals(expectedToString, actualWebDriverConfig.toString());
    }
}