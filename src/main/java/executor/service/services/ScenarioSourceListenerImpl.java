package executor.service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import exceptions.EmptyFileException;
import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

@Service
public class ScenarioSourceListenerImpl implements ScenarioSourceListener {

    private final String file;
    private final ScenarioExecutor scenarioExecutor;
    private final WebDriver webDriver;

    public ScenarioSourceListenerImpl(@Value("${path.to.scenario_source}") String file, ScenarioExecutor scenarioExecutor, WebDriver webDriver) {
        this.file = file;
        this.scenarioExecutor = scenarioExecutor;
        this.webDriver = webDriver;
    }

    @Override
    public void execute() {
        try {
            List<Scenario> scenarioList = getScenarios();
            for (Scenario scenario : scenarioList) {
                scenarioExecutor.execute(scenario, webDriver);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public String readFile() throws FileNotFoundException, EmptyFileException {
        Scanner scanner;
        String result;
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
        if (in == null) {
            throw new FileNotFoundException("File not found: " + file);
        }
        scanner = new Scanner(in).useDelimiter("\\A");
        result = scanner.hasNext() ? scanner.next() : "";
        if (result.isEmpty()) {
            throw new EmptyFileException("File is empty: " + file);
        }
        return result;
    }

    public List<Scenario> getScenarios() throws IOException {
        List<Scenario> scenarios;
        ObjectMapper objectMapper;
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            scenarios = objectMapper.readValue(readFile(), new TypeReference<List<Scenario>>() {
            });
            if (scenarios.isEmpty()) {
                throw new EmptyFileException("File contains no scenarios: " + file);
            }
        } catch (JsonProcessingException e) {
            throw new IOException("Failed to parse JSON: " + e.getMessage(), e);
        }
        return scenarios;
    }
}
