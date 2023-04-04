package executor.service.model;

import executor.service.model.Scenario;
import executor.service.model.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class ScenarioTest {

    private final String name = "name";
    private final String site = "site";
    private final List<Step> steps = new ArrayList<>();

    private Scenario actualScenario;
    private Scenario emptyScenario;

    @BeforeEach
    public void setUp() {
        actualScenario = new Scenario(name, site, steps);
        emptyScenario = new Scenario();
    }

    @Test
    public void testNotNull() {
        Assertions.assertNotNull(actualScenario);
    }

    @Test
    public void testObjectsAreEqual() {
        Scenario expectedScenario = new Scenario(name, site, steps);
        Assertions.assertEquals(expectedScenario, actualScenario);
    }

    @Test
    public void testObjectsAreNotEqual() {
        Scenario expectedScenario = new Scenario("name1", "site1", new ArrayList<>());
        Assertions.assertNotEquals(expectedScenario, actualScenario);
    }

    @Test
    public void testConstructor() {
        Assertions.assertEquals(name, actualScenario.getName());
        Assertions.assertEquals(site, actualScenario.getSite());
        Assertions.assertEquals(steps, actualScenario.getSteps());
    }

    @Test
    public void testSetters() {
        emptyScenario.setName(name);
        emptyScenario.setSite(site);
        emptyScenario.setSteps(steps);
        Assertions.assertEquals(actualScenario, emptyScenario);
    }

    @Test
    public void testHashCode() {
        Scenario expectedScenario = new Scenario(name, site, steps);
        Assertions.assertEquals(expectedScenario.hashCode(), actualScenario.hashCode());
    }

    @Test
    public void testToString() {
        String expectedScenario = "Scenario{name='name', site='site', steps=[]}";
        Assertions.assertEquals(expectedScenario, actualScenario.toString());
    }

    @Test
    public void testEmptyConstructor() {
        Assertions.assertNull(emptyScenario.getName());
        Assertions.assertNull(emptyScenario.getSite());
        Assertions.assertNull(emptyScenario.getSteps());
    }

    @Test
    public void testSetNameWithNull() {
        actualScenario.setName(null);
        Assertions.assertNull(actualScenario.getName());
    }

    @Test
    public void testSetSiteWithNull() {
        actualScenario.setSite(null);
        Assertions.assertNull(actualScenario.getSite());
    }

    @Test
    public void testSetStepsWithNull() {
        actualScenario.setSteps(null);
        Assertions.assertNull(actualScenario.getSteps());
    }

    @Test
    public void testToStringWithNullValues() {
        String expectedScenario = "Scenario{name='null', site='null', steps=null}";
        Assertions.assertEquals(expectedScenario, emptyScenario.toString());
    }
}