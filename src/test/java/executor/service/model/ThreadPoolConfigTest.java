package executor.service.model;

import executor.service.model.ThreadPoolConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ThreadPoolConfigTest {

    private final Integer corePoolSize = 1;
    private final Long keepAliveTime = 1L;

    private ThreadPoolConfig actualThreadPoolConfig;
    private ThreadPoolConfig emptyThreadPoolConfig;

    @BeforeEach
    public void setUp() {
        actualThreadPoolConfig = new ThreadPoolConfig(corePoolSize, keepAliveTime);
        emptyThreadPoolConfig = new ThreadPoolConfig();
    }

    @Test
    public void testNotNull() {
        Assertions.assertNotNull(actualThreadPoolConfig);
    }

    @Test
    public void testObjectsAreEqual() {
        ThreadPoolConfig expectedThreadPoolConfig = new ThreadPoolConfig(corePoolSize, keepAliveTime);
        Assertions.assertEquals(expectedThreadPoolConfig, actualThreadPoolConfig);
    }

    @Test
    public void testObjectsAreNotEqual() {
        ThreadPoolConfig expectedThreadPoolConfig = new ThreadPoolConfig(2, 2L);
        Assertions.assertNotEquals(expectedThreadPoolConfig, actualThreadPoolConfig);
    }

    @Test
    public void testConstructor() {
        Assertions.assertEquals(corePoolSize, actualThreadPoolConfig.getCorePoolSize());
        Assertions.assertEquals(keepAliveTime, actualThreadPoolConfig.getKeepAliveTime());
    }

    @Test
    public void testSetters() {
        emptyThreadPoolConfig.setCorePoolSize(corePoolSize);
        emptyThreadPoolConfig.setKeepAliveTime(keepAliveTime);
        Assertions.assertEquals(corePoolSize, emptyThreadPoolConfig.getCorePoolSize());
        Assertions.assertEquals(keepAliveTime, emptyThreadPoolConfig.getKeepAliveTime());
    }

    @Test
    public void testHashCode() {
        ThreadPoolConfig expectedThreadPoolConfig = new ThreadPoolConfig(corePoolSize, keepAliveTime);
        Assertions.assertEquals(expectedThreadPoolConfig.hashCode(), actualThreadPoolConfig.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "ThreadPoolConfig{corePoolSize=1, keepAliveTime=1}";
        Assertions.assertEquals(expectedToString,actualThreadPoolConfig.toString());
    }

    @Test
    public void testEmptyConstructor() {
        Assertions.assertNull(emptyThreadPoolConfig.getCorePoolSize());
        Assertions.assertNull(emptyThreadPoolConfig.getKeepAliveTime());
    }

    @Test
    public void testSetCorePoolSizeWithNull() {
        actualThreadPoolConfig.setCorePoolSize(null);
        Assertions.assertNull(actualThreadPoolConfig.getCorePoolSize());
    }

    @Test
    public void testSetKeepAliveTimeWithNull() {
        actualThreadPoolConfig.setKeepAliveTime(null);
        Assertions.assertNull(actualThreadPoolConfig.getKeepAliveTime());
    }

    @Test
    public void testToStringWithNullValues() {
        String expectedToString = "ThreadPoolConfig{corePoolSize=null, keepAliveTime=null}";
        Assertions.assertEquals(expectedToString, emptyThreadPoolConfig.toString());
    }
}
