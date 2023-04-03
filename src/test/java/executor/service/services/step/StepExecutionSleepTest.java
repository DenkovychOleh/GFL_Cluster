package executor.service.services.step;

import executor.service.model.Step;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openqa.selenium.WebDriver;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StepExecutionSleepTest {

    private final String sleep = "sleep";

    @Mock
    private WebDriver webDriver;

    @InjectMocks
    private StepExecutionSleep stepExecutionSleep;

    private Step step;

    @BeforeEach
    public void setUp() {
        step = new Step(sleep, "2000");
    }

    @Test
    public void notNullExecution(){
        assertNotNull(stepExecutionSleep);
    }

    @Test
    public void getSleepAction(){
        String actualAction = step.getAction();
        assertEquals(sleep, actualAction);
    }

    @Test
    public void setSleep(){
        long expectedTimeSleep = 2000;
        Instant start = Instant.now();
        stepExecutionSleep.step(webDriver, step);
        Instant end = Instant.now();

        long actualTimeSleep = Duration.between(start,end).toMillis();
        assertTrue(expectedTimeSleep <= actualTimeSleep);
    }

    @Test
    public void numberFormatExceptionValueNotInt() {
        Step stepWrong = new Step(sleep, "wrong");
        assertThrows(IllegalArgumentException.class, () -> stepExecutionSleep.step(webDriver, stepWrong));
    }

    @Test
    public void runTimeException(){
        Step stepWrong = new Step(sleep, "-2000");
        assertThrows(RuntimeException.class, () -> stepExecutionSleep.step(webDriver, stepWrong));
    }
}