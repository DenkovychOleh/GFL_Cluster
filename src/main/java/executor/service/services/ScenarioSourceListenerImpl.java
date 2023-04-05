package executor.service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.Scenario;
import org.openqa.selenium.WebDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Scanner;

public class ScenarioSourceListenerImpl implements ScenarioSourceListener {

    private String file;
    private ScenarioExecutor scenarioExecutor;
    private WebDriver webDriver;

    public ScenarioSourceListenerImpl(String file, ScenarioExecutor scenarioExecutor, WebDriver webDriver) {
        this.file = file;
        this.scenarioExecutor = scenarioExecutor;
        this.webDriver = webDriver;
    }

    @Override
    public void execute() {
        try {
            List<Scenario> scenarioList = readFile();
            for (Scenario scenario : scenarioList) {
                scenarioExecutor.execute(scenario, webDriver);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<Scenario> readFile() throws IOException {
        List<Scenario> scenarios;
        ObjectMapper objectMapper;
        Scanner scanner;
        String result;
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(file);
        if (in == null) {
            throw new IOException("File not found: " + file);
        }
        scanner = new Scanner(in).useDelimiter("\\A");
        result = scanner.hasNext() ? scanner.next() : "";
        if (result.isEmpty()) {
            throw new IOException("File is empty: " + file);
        }
        objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        try {
            scenarios = objectMapper.readValue(result, new TypeReference<List<Scenario>>() {
            });
            if (scenarios.isEmpty()) {
                throw new IOException("File contains no scenarios: " + file);
            }
        } catch (JsonProcessingException e) {
            throw new IOException("Failed to parse JSON: " + e.getMessage(), e);
        }
        return scenarios;
    }
}
