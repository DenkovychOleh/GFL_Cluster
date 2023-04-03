package executor.service;

import executor.service.model.Step;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


public class StepTest {

    private final String action = "click";
    private final String value = "button";

    @Test
    public void testObjectsAreEqual() {
        Step expectedObject = new Step(action, value);
        Step actualObject = new Step(action, value);
        Assertions.assertEquals(expectedObject, actualObject);
    }

    @Test
    public void testConstructorAndGetters() {
        Step step = new Step(action, value);
        Assertions.assertEquals(action, step.getAction());
        Assertions.assertEquals(value, step.getValue());
    }

    @Test
    public void testSetters() {
        Step step = new Step();
        step.setAction(action);
        step.setValue(value);
        Assertions.assertEquals(action, step.getAction());
        Assertions.assertEquals(value, step.getValue());
    }

    @Test
    public void testEqualsAndHashCode() {
        Step expectedObject = new Step(action, value);
        Step actualObject = new Step(action, value);
        Assertions.assertEquals(expectedObject, actualObject);
        Assertions.assertEquals(expectedObject.hashCode(), actualObject.hashCode());
    }

    @Test
    public void testToString() {
        Step step = new Step(action, value);
        String expected = "Step{action='click', value='button'}";
        Assertions.assertEquals(expected, step.toString());
    }

    @Test
    public void testEqualsWithSameObject() {
        Step step = new Step(action, value);
        Assertions.assertEquals(step, step);
    }

    @Test
    public void testEqualsWithDifferentObjectType() {
        Step actualObject = new Step(action, value);
        String otherObject = "not a Step object";
        Assertions.assertNotEquals(actualObject, otherObject);
    }

    @Test
    public void testNotEquals() {
        Step expectedObject = new Step(action, value);
        Step actualObject = new Step("input", "email");
        Assertions.assertNotEquals(expectedObject, actualObject);
    }

    @Test
    public void testEmptyConstructor() {
        Step step = new Step();
        Assertions.assertNull(step.getAction());
        Assertions.assertNull(step.getValue());
    }

    @Test
    public void testSetActionWithNull() {
        Step step = new Step(action, value);
        step.setAction(null);
        Assertions.assertNull(step.getAction());
    }

    @Test
    public void testSetValueWithNull() {
        Step step = new Step(action, value);
        step.setValue(null);
        Assertions.assertNull(step.getValue());
    }

    @Test
    public void testToStringWithNullValues() {
        Step step = new Step(null, null);
        String expected = "Step{action='null', value='null'}";
        Assertions.assertEquals(expected, step.toString());
    }
}
