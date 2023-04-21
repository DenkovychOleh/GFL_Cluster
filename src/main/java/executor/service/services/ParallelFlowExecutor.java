package executor.service.services;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.Scenario;
import executor.service.model.ThreadPoolConfig;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
@Service
public class ParallelFlowExecutor {

    private ThreadPoolConfig threadPoolConfig;
    private final ScenarioSourceListenerImpl scenarioSourceListener;
    private final ProxySourcesClient proxySourcesClient;
    private final ExecutionService executionService;
    private final ThreadPoolExecutor threadPoolExecutor;
    private static  int proxyConfigHolderIndex = 0;

    public ParallelFlowExecutor(ScenarioSourceListenerImpl scenarioSourceListener,
                                ProxySourcesClient proxySourcesClient,
                                ExecutionService executionService,
                                ThreadPoolExecutor threadPoolExecutor) {
        threadPoolConfig = readAppProperties();
        this.scenarioSourceListener = scenarioSourceListener;
        this.proxySourcesClient = proxySourcesClient;
        this.executionService = executionService;
        this.threadPoolExecutor = threadPoolExecutor;
        threadPoolExecutor.setCorePoolSize(threadPoolConfig.getCorePoolSize());
        threadPoolExecutor.setKeepAliveTime(threadPoolConfig.getKeepAliveTime(), TimeUnit.MILLISECONDS);
    }


    private ThreadPoolConfig readAppProperties() {
        Parameters params = new Parameters();
        FileBasedConfigurationBuilder<FileBasedConfiguration> builder =
                new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                        .configure(params.properties()
                                .setFileName("application.properties"));
        try {
            Configuration config = builder.getConfiguration();
            return threadPoolConfig = new ThreadPoolConfig(config.getInt("webdriver.config.threadsCount"),
                    config.getLong("webdriver.config.pageLoadTimeout"));
        }
        catch(org.apache.commons.configuration2.ex.ConfigurationException configEx) {
            configEx.getStackTrace();
        }
        return threadPoolConfig;
    }

    public void runThread() {
        int scenarioIndex = 0;
        while (true) {
            Scenario scenario;
            try {
                scenario = scenarioSourceListener.getScenarios().get(scenarioIndex);
                scenarioIndex++;
                if (scenario == null) break;
                executeScenarioInThread(scenario);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        threadPoolExecutor.shutdown();
    }

    private void executeScenarioInThread(Scenario scenario) {
        ProxyConfigHolder proxyConfigHolder;
        try {
            proxyConfigHolder =  proxySourcesClient.getProxy("ProxyCredentials.json",
                    "ProxyNetworkConfig.json").get(proxyConfigHolderIndex);
            proxyConfigHolderIndex++;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        threadPoolExecutor.execute(() -> executionService.execute(scenario, proxyConfigHolder));

    }
}
