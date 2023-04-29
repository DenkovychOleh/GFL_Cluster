package executor.service.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import executor.service.model.Scenario;
import executor.service.model.Step;
import executor.service.services.ScenarioSourceListenerImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(ScenarioSourceController.class)
class ScenarioSourceControllerTest {

    private ObjectMapper objectMapper;
    private Scenario testScenario;
    private List<Scenario> testScenarioList;

    @MockBean
    private ScenarioSourceListenerImpl ScenarioSourceListenerImpl;

    @Autowired
    private MockMvc mockMvc;

    @BeforeEach
    public void setUp() throws IOException {
        Step step1 = new Step("clickCSS", "body > ul > li:nth-child(1) > a");
        Step step2 = new Step("clickXpath", "/html/body/p");
        Step step3 = new Step("sleep", "5000");
        Step step4 = new Step("clickCSS", "body > ul > li:nth-child(1) > a");
        Step step5 = new Step("clickXpath", "/html/body/p");
        Scenario tempScenario = new Scenario("test scenario 2", "http://info.cern.ch", Arrays.asList(step1, step2, step3));
        testScenario = new Scenario("test scenario 1", "http://info.cern.ch", Arrays.asList(step4, step5));
        testScenarioList = Arrays.asList(testScenario, tempScenario);
        objectMapper = new ObjectMapper();
    }

    @Test
    public void testAddValidScenarioStatusIsOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/scenario")
                        .content(objectMapper.writeValueAsString(testScenario))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddValidScenarioListStatusIsOK() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/scenario/list")
                        .content(objectMapper.writeValueAsString(testScenarioList))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    public void testAddScenarioWithEmptySite() throws Exception {
        String expectedResult = "{\"site\":\"site is required\"}";
        testScenario.setSite("");
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario")
                .content(objectMapper.writeValueAsString(testScenario))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

    @Test
    public void testAddScenarioWhereStepsAreNull() throws Exception {
        String expectedResult = "{\"steps\":\"steps are required\"}";
        testScenario.setSteps(null);
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario")
                .content(objectMapper.writeValueAsString(testScenario))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

    @Test
    public void testAddScenarioWhereActionIsEmpty() throws Exception {
        String expectedResult = "{\"steps[0].action\":\"action is required\"}";
        testScenario.getSteps().get(0).setAction("");
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario")
                .content(objectMapper.writeValueAsString(testScenario))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

    @Test
    public void testAddScenarioWhereValueIsEmpty() throws Exception {
        String expectedResult = "{\"steps[0].value\":\"value is required\"}";
        testScenario.getSteps().get(0).setValue("");
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario")
                .content(objectMapper.writeValueAsString(testScenario))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

    @Test
    public void testAddScenarioListToScenarioEndpoint() throws Exception {
        String expectedResult = "Invalid request body. Expected Scenario object.";
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario")
                .content(objectMapper.writeValueAsString(testScenarioList))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

    @Test
    public void testAddScenarioListWithEmptySite() throws Exception {
        String expectedResult = "{\"scenarioList[0].site\":\"site is required\"}";
        testScenarioList.get(0).setSite("");
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario/list")
                .content(objectMapper.writeValueAsString(testScenarioList))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

    @Test
    public void testAddScenarioListWhereStepsAreNull() throws Exception {
        String expectedResult = "{\"scenarioList[1].steps\":\"steps are required\"}";
        testScenarioList.get(1).setSteps(null);
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario/list")
                .content(objectMapper.writeValueAsString(testScenarioList))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

    @Test
    public void testAddScenarioListWhereActionIsEmpty() throws Exception {
        String expectedResult = "{\"scenarioList[0].steps[1].action\":\"action is required\"}";
        testScenarioList.get(0).getSteps().get(1).setAction("");
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario/list")
                .content(objectMapper.writeValueAsString(testScenarioList))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

    @Test
    public void testAddScenarioListWhereValueIsEmpty() throws Exception {
        String expectedResult = "{\"scenarioList[0].steps[0].value\":\"value is required\"}";
        testScenarioList.get(0).getSteps().get(0).setValue("");
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario/list")
                .content(objectMapper.writeValueAsString(testScenarioList))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

    @Test
    public void testAddScenarioToScenarioListEndpoint() throws Exception {
        String expectedResult = "Invalid request body. Expected a list of Scenario objects.";
        RequestBuilder request = MockMvcRequestBuilders.post("/api/scenario/list")
                .content(objectMapper.writeValueAsString(testScenario))
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(request).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(expectedResult, response.getContentAsString());
    }

}