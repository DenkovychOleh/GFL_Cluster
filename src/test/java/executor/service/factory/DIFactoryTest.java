package executor.service.factory;

import executor.service.model.Step;
import executor.service.services.step.StepExecution;
import executor.service.services.step.StepExecutionClickCSS;
import executor.service.services.step.StepExecutionClickXpath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DIFactoryTest {
    private DIFactory diFactory;
    private List<Step> steps = new ArrayList<>();
    private StepExecutionClickCSS stepExecutionClickCSS = new StepExecutionClickCSS();
    private StepExecutionClickXpath stepExecutionClickXpath = new StepExecutionClickXpath();

    @BeforeEach
    public void setUp() {
        diFactory = DIFactory.init();
        steps.add(new Step("clickXpath", "Click first X path scenario..."));
        steps.add(new Step("click", "Click first CSS scenario..."));
        steps.add(new Step("click", "Click second CSS scenario..."));
        steps.add(new Step("clickXpath", "Click second X path scenario..."));
    }

    @AfterEach
    public void tearDown() {
    }

    @Test
    public void createDIStepExecutionList() {
        List<String> expected = new ArrayList<>();
        expected.add("clickXpath");
        expected.add("click");
        expected.add("click");
        expected.add("clickXpath");
        TypeReference<List<StepExecution>> stepExecution = new TypeReference<List<StepExecution>>() {};
        List<StepExecution> stepExecutions = diFactory.create(stepExecution);
        List<String> executionActions = steps.stream()
                .map(step -> stepExecutions.stream()
                        .filter(exec -> exec.getStepAction().equals(step.getAction()))
                        .findFirst()
                        .get()
                        .getStepAction())
                .collect(Collectors.toList());

        assertLinesMatch(expected, executionActions);
    }

    @Test
    public void createDIStepExecutionClickCss() {
        StepExecutionClickCSS stepExecutionActual = diFactory.create(StepExecutionClickCSS.class);
        String stepAction = stepExecutionActual.getStepAction();

        assertTrue(stepExecutionActual.getClass().isAssignableFrom(StepExecutionClickCSS.class));
        assertEquals(stepExecutionClickCSS.getClass().getSimpleName(), stepExecutionActual.getClass().getSimpleName());
        assertEquals("click", stepAction);
    }

    @Test
    public void createDIStepExecutionClickXpath() {
        StepExecutionClickXpath stepExecutionActual = diFactory.create(StepExecutionClickXpath.class);
        String stepAction = stepExecutionActual.getStepAction();

        assertTrue(stepExecutionActual.getClass().isAssignableFrom(StepExecutionClickXpath.class));
        assertEquals(stepExecutionClickXpath.getClass().getSimpleName(), stepExecutionActual.getClass().getSimpleName());
        assertEquals("clickXpath", stepAction);
    }
}