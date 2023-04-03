package executor.service;

import executor.service.model.ProxyCredentials;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ProxyCredentialsTest {

    private final String username = "username";
    private final String password = "password";

    private ProxyCredentials actualCredentials;
    private ProxyCredentials emptyCredentials;

    @BeforeEach
    public void setUp(){
        actualCredentials = new ProxyCredentials(username, password);
        emptyCredentials = new ProxyCredentials();
    }

    @Test
    public void testNotNull() {
        Assertions.assertNotNull(actualCredentials);
    }

    @Test
    public void testEqualsDTO() {
        ProxyCredentials expectedCredentials = new ProxyCredentials(username, password);
        Assertions.assertEquals(expectedCredentials, actualCredentials);
    }

    @Test
    public void testNotEqualsDTO() {
        ProxyCredentials newCredentials = new ProxyCredentials("testUsername", "testPassword");
        Assertions.assertNotEquals(newCredentials, actualCredentials);
    }

    @Test
    public void testConstructor() {
        Assertions.assertEquals(username, actualCredentials.getUsername());
        Assertions.assertEquals(password, actualCredentials.getPassword());
    }

    @Test
    public void testSettersAndGetters() {
        emptyCredentials.setUsername(username);
        emptyCredentials.setPassword(password);
        Assertions.assertEquals(username, emptyCredentials.getUsername());
        Assertions.assertEquals(password, emptyCredentials.getPassword());
    }

    @Test
    public void testHashCode() {
        ProxyCredentials expectedCredentials = new ProxyCredentials(username, password);
        Assertions.assertEquals(expectedCredentials.hashCode(), actualCredentials.hashCode());
    }

    @Test
    public void testToString() {
        String expectedToString = "ProxyCredentials{username='username', password='password'}";
        Assertions.assertEquals(expectedToString, actualCredentials.toString());
    }

    @Test
    public void testEmptyConstructor() {
        Assertions.assertNull(emptyCredentials.getUsername());
        Assertions.assertNull(emptyCredentials.getPassword());
    }

    @Test
    public void testSetUsernameWithNull() {
        actualCredentials.setUsername(null);
        Assertions.assertNull(actualCredentials.getUsername());
    }

    @Test
    public void testSetPasswordWithNull() {
        actualCredentials.setPassword(null);
        Assertions.assertNull(actualCredentials.getPassword());
    }

    @Test
    public void testToStringWithNullValues() {
        String expectedToString = "ProxyCredentials{username='null', password='null'}";
        Assertions.assertEquals(expectedToString, emptyCredentials.toString());
    }
}