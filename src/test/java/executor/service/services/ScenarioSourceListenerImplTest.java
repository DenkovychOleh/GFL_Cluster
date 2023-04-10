package executor.service.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.EmptyFileException;
import executor.service.model.Scenario;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static org.mockito.Mockito.*;

public class ScenarioSourceListenerImplTest {

    private ScenarioSourceListenerImpl listener;
    private ScenarioExecutor executor;
    private WebDriver driver;
    private String validData = "src/main/resources/validData.json";
    private String invalidData = "src/main/resources/invalidData.json";
    private String emptyFile = "src/main/resources/emptyFile.json";
    private File file;

    @BeforeEach
    void setUp() {
        executor = mock(ScenarioExecutor.class);
        driver = mock(WebDriver.class);
    }

    @Test
    public void testObjectsAreEqualOnGetScenarios() throws IOException {
        file = new File(validData);
        if (file.exists()) {
            ObjectMapper objectMapper = new ObjectMapper();

            listener = new ScenarioSourceListenerImpl(file.getName(), executor, driver);
            List<Scenario> actualObject = listener.getScenarios();

            InputStream in = this.getClass().getClassLoader().getResourceAsStream(file.getName());
            Scanner scanner = new Scanner(in).useDelimiter("\\A");
            String result = scanner.hasNext() ? scanner.next() : "";
            List<Scenario> expectedObject = objectMapper.readValue(result, new TypeReference<List<Scenario>>() {
            });

            Assertions.assertEquals(expectedObject, actualObject);
        }
    }

    @Test()
    public void testFileNotFoundExceptionOnReadFile() {
        file = new File("fileNotExist.json");
        listener = new ScenarioSourceListenerImpl(file.getName(), executor, driver);
        Assertions.assertThrows(FileNotFoundException.class, () -> listener.readFile());
    }

    @Test
    public void testEmptyFileExceptionOnReadFile() {
        file = new File(emptyFile);
        if (file.exists()) {
            listener = new ScenarioSourceListenerImpl(file.getName(), executor, driver);
            Assertions.assertThrows(EmptyFileException.class, () -> listener.readFile());
        }
    }

    @Test
    public void testEmptyFileExceptionOnGetScenarios() {
        file = new File(emptyFile);
        if (file.exists()) {
            listener = new ScenarioSourceListenerImpl(file.getName(), executor, driver);
            Assertions.assertThrows(EmptyFileException.class, () -> listener.getScenarios());
        }
    }

    @Test
    public void testInvalidDataGetScenarios() {
        file = new File(invalidData);
        if (file.exists()) {
            listener = new ScenarioSourceListenerImpl(file.getName(), executor, driver);
            Assertions.assertThrows(IOException.class, () -> listener.getScenarios());
        }
    }

}
