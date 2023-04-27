package executor.service.controllers;

import executor.service.model.Scenario;
import executor.service.services.ScenarioSourceListener;
import executor.service.services.ScenarioSourceListenerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/scenario")
public class ScenarioSourceController {

    private final ScenarioSourceListener scenarioSourceListener;

    public ScenarioSourceController(ScenarioSourceListener scenarioSourceListener) {
        this.scenarioSourceListener = scenarioSourceListener;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addScenario(@RequestBody Scenario scenario) {
        ((ScenarioSourceListenerImpl) scenarioSourceListener).setScenario(scenario);
        scenarioSourceListener.execute();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<HttpStatus> addScenarioList(@RequestBody List<Scenario> scenarioList) {
        ((ScenarioSourceListenerImpl) scenarioSourceListener).setScenarioList(scenarioList);
        scenarioSourceListener.execute();
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
