package executor.service.services;

import executor.service.model.Scenario;
import executor.service.model.ThreadPoolConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ParallelFlowExecutorTest {

    @Mock
    private ScenarioSourceListenerImpl sourceListener;
    @Mock
    private ProxySourcesClient sourcesClient;
    @Mock
    private ThreadPoolExecutor threadPoolExecutor;
    @Spy
    private ThreadPoolConfig threadPoolConfig = new ThreadPoolConfig(2, 30000L);
    @InjectMocks
    private ParallelFlowExecutor parallelFlowExecutor;

    @Test
    public void setThreadPoolExecutorConfigs() {
        verify(threadPoolExecutor).setCorePoolSize(threadPoolConfig.getCorePoolSize());
        verify(threadPoolExecutor).setKeepAliveTime(threadPoolConfig.getKeepAliveTime(), TimeUnit.MILLISECONDS);
    }
    @Test
    public void ifScenarioNullShutdown() {
        try {
            for (int i = 0; i < sourceListener.getScenarios().size(); i++) {
                when(sourceListener.getScenarios().get(i)).thenReturn(null);
                parallelFlowExecutor.runThread();

                verifyNoInteractions(sourcesClient);
                verify(threadPoolExecutor, times(0)).execute(any());
                verify(threadPoolExecutor).shutdown();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    @Test
    public void executeRunnableInParallel() {
        Scenario scenario = new Scenario();
        try {
            for (int i = 0; i < sourceListener.getScenarios().size(); i++) {
                when(sourceListener.getScenarios().get(i))
                        .thenReturn(scenario)
                        .thenReturn(null);
                parallelFlowExecutor.runThread();

                verify(sourcesClient).getProxy("ProxyCredentials.json", "ProxyNetworkConfig.json");
                verify(threadPoolExecutor).execute(any());
                verify(threadPoolExecutor).shutdown();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void executeFourThread(){
        Scenario scenario = new Scenario();
        int scenarioAmount = 4;
        try {
            for (int i = 0; i < sourceListener.getScenarios().size(); i++) {
                when(sourceListener.getScenarios().get(i))
                        .thenReturn(scenario)
                        .thenReturn(scenario)
                        .thenReturn(scenario)
                        .thenReturn(scenario)
                        .thenReturn(null);

                parallelFlowExecutor.runThread();

                verify(sourcesClient, times(scenarioAmount)).getProxy("ProxyCredentials.json",
                        "ProxyNetworkConfig.json");
                verify(threadPoolExecutor, times(scenarioAmount)).execute(any());
                verify(threadPoolExecutor).shutdown();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}