package executor.service.controllers;

import executor.service.model.Scenario;
import executor.service.services.ScenarioSourceListener;
import executor.service.services.ScenarioSourceListenerImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

@RequestMapping("/api/scenario")
@RestController
@Validated
public class ScenarioSourceController {

    private final ScenarioSourceListener scenarioSourceListener;

    public ScenarioSourceController(ScenarioSourceListener scenarioSourceListener) {
        this.scenarioSourceListener = scenarioSourceListener;
    }

    @PostMapping
    public ResponseEntity<HttpStatus> addScenario(@RequestBody @Valid Scenario scenario) {
        ((ScenarioSourceListenerImpl) scenarioSourceListener).setScenario(scenario);
        scenarioSourceListener.execute();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/list")
    public ResponseEntity<HttpStatus> addScenarioList(@RequestBody @Valid List<Scenario> scenarioList) {
        ((ScenarioSourceListenerImpl) scenarioSourceListener).setScenarioList(scenarioList);
        scenarioSourceListener.execute();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        Map<String, String> errors = new HashMap<>();
        exception.getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleMethodArgumentTypeMismatchException(HttpMessageNotReadableException exception) {
        String message;
        String jsonType = exception.getMessage().substring(120, 131);
        if (jsonType.equals("START_ARRAY")) {
            message = "Invalid request body. Expected Scenario object.";
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
        }
        message = "Invalid request body. Expected a list of Scenario objects.";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleConstraintViolationException(ConstraintViolationException exception) {
        Map<String, String> errors = new HashMap<>();
        Set<ConstraintViolation<?>> violations = exception.getConstraintViolations();
        violations.forEach(violation -> {
            String field = violation.getPropertyPath().toString().replace("addScenarioList.", "");
            String message = violation.getMessage();
            errors.put(field, message);
        });
        return errors;
    }


}