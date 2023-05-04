package executor.service.services;

import executor.service.model.Scenario;
import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
class ScenarioSourceQueueHandlerImplTest {
    private ScenarioSourceQueueHandler sourceQueueHandler;
    private List<Step> stepList;
    private Scenario scenario;

    @BeforeEach
    public void setUp() {
        sourceQueueHandler = new ScenarioSourceQueueHandlerImpl();
        stepList = Arrays.asList(new Step("Action1", "Value1"), new Step("Action2", "Value2"));
        scenario = new Scenario("Name", "Site", stepList);
    }

    @Test
    public void stepListIsNotNull(){
        assertNotNull(stepList);
    }
    
    @Test
    public void pollFromEmptyQueueHandler(){
        assertNull(sourceQueueHandler.poll());
    }

    @Test
    public void pollFromNotEmptyQueueHandler(){
        sourceQueueHandler.offer(scenario);
        assertNotNull(sourceQueueHandler.poll());
    }

    @Test
    public void equalsObjectInQueueHandler(){
        sourceQueueHandler.offer(scenario);
        assertEquals(scenario, sourceQueueHandler.poll());
    }

    @Test
    public void isEmptyQueueHandler(){
        assertTrue(sourceQueueHandler.isEmpty());
    }

    @Test
    public void isNotEmptyQueueHandler(){
        sourceQueueHandler.offer(scenario);
        assertFalse(sourceQueueHandler.isEmpty());
    }
}