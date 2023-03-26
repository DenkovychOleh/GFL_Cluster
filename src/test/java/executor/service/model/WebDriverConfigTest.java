package executor.service.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WebDriverConfigTest {
    private final String webDriverExecutable = "WebDriver";
    private final String userAgent = "User agent";
    private final Long pageLoadTimeout = 10L;
    private final Long implicitlyWait = 15L;

    private WebDriverConfig actualWebDriverConfig;
    private WebDriverConfig emptyWebDriverConfig;

    @BeforeEach
    void setUp() {
        actualWebDriverConfig = new WebDriverConfig(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);
        emptyWebDriverConfig = new WebDriverConfig();
    }

    @Test
    void testNotNull() {
        Assertions.assertNotNull(actualWebDriverConfig);
        Assertions.assertNotNull(emptyWebDriverConfig);
    }

    @Test
    void testEqualsObjects() {
        WebDriverConfig expectedCredentials = new WebDriverConfig(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);
        Assertions.assertEquals(expectedCredentials, actualWebDriverConfig);
    }

    @Test
    void testNotEqualsObjects() {
        WebDriverConfig newCredentials = new WebDriverConfig("NotWebDriver", "Agent", 6L, 12L);
        Assertions.assertNotEquals(newCredentials, actualWebDriverConfig);
    }

    @Test
    void testConstructor() {
        Assertions.assertEquals(webDriverExecutable, actualWebDriverConfig.getWebDriverExecutable());
        Assertions.assertEquals(userAgent, actualWebDriverConfig.getUserAgent());
        Assertions.assertEquals(pageLoadTimeout, actualWebDriverConfig.getPageLoadTimeout());
        Assertions.assertEquals(implicitlyWait, actualWebDriverConfig.getImplicitlyWait());
    }

    @Test
    void testEmptyConstructor() {
        Assertions.assertNull(emptyWebDriverConfig.getWebDriverExecutable());
        Assertions.assertNull(emptyWebDriverConfig.getUserAgent());
        Assertions.assertNull(emptyWebDriverConfig.getPageLoadTimeout());
        Assertions.assertNull(emptyWebDriverConfig.getImplicitlyWait());
    }

    @Test
    void testSettersAndGetters() {
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
    void testHashCode() {
        WebDriverConfig expectedCredentials = new WebDriverConfig(webDriverExecutable, userAgent, pageLoadTimeout, implicitlyWait);
        Assertions.assertEquals(expectedCredentials.hashCode(), actualWebDriverConfig.hashCode());
    }

    @Test
    void testSetWebDriverExecutableForNull() {
        actualWebDriverConfig.setWebDriverExecutable(null);
        Assertions.assertNull(actualWebDriverConfig.getWebDriverExecutable());
    }

    @Test
    void testSetUserAgentForNull() {
        actualWebDriverConfig.setUserAgent(null);
        Assertions.assertNull(actualWebDriverConfig.getUserAgent());
    }

    @Test
    void testSetPageLoadTimeoutForNull() {
        actualWebDriverConfig.setPageLoadTimeout(null);
        Assertions.assertNull(actualWebDriverConfig.getPageLoadTimeout());
    }

    @Test
    void testSetImplicitlyWaitForNull() {
        actualWebDriverConfig.setImplicitlyWait(null);
        Assertions.assertNull(actualWebDriverConfig.getImplicitlyWait());
    }

    @Test
    void testToStringForNullValues() {
        String expectedToString = "WebDriverConfig{webDriverExecutable='null', userAgent='null', pageLoadTimeout=null, implicitlyWait=null}";
        Assertions.assertEquals(expectedToString, emptyWebDriverConfig.toString());
    }

    @Test
    void testToString() {
        String expectedToString = "WebDriverConfig{webDriverExecutable='WebDriver', userAgent='User agent', pageLoadTimeout=10, implicitlyWait=15}";
        Assertions.assertEquals(expectedToString, actualWebDriverConfig.toString());
    }
}