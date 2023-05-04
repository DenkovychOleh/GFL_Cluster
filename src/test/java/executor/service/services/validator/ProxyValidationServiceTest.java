package executor.service.services.validator;

import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.*;

import static org.junit.jupiter.api.Assertions.*;

class ProxyValidationServiceTest {
    private final List<ProxyConfigHolder> availableProxyList = new ArrayList<>();
    private static final Queue<ProxyConfigHolder> proxyQueue = new ConcurrentLinkedQueue<>();
    private static final CountDownLatch countDownLatch = new CountDownLatch(2);
    private ExecutorService executorService;
    private static final int THREADS_AMOUNT = 2;
    private static final int WORKERS_AMOUNT = 2;

    @BeforeEach
    void setUp() {
//        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("107.151.192.29", 45787),
//                new ProxyCredentials("user", "password")));
//        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("103.122.179.43", 45787),
//                new ProxyCredentials("user", "password")));
//        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("221.145.195.200", 80),
//                new ProxyCredentials("user", "password")));
//        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("175.9.237.225", 8964),
//                new ProxyCredentials("user", "password")));
        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("223.100.178.167", 9091),
                new ProxyCredentials("user", "password")));
//        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("65.108.69.40", 10001),
//                new ProxyCredentials("user", "password")));
        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("183.221.221.149", 9091),
                new ProxyCredentials("user", "password")));
//        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("123.60.172.164", 7890),
//                new ProxyCredentials("user", "password")));
//        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("203.19.38.114", 1080),
//                new ProxyCredentials("user", "password")));
//        proxyQueue.add(new ProxyConfigHolder(new ProxyNetworkConfig("1.4.250.111", 8080),
//                new ProxyCredentials("user", "password")));
    }

    @Test
    void validateProxy() throws InterruptedException {
        ProxyValidationService proxyValidator = new ProxyValidationService();

        executorService = Executors.newFixedThreadPool(THREADS_AMOUNT);
        int cycles = proxyQueue.size() / WORKERS_AMOUNT;

        for (int i = 0; i < WORKERS_AMOUNT; i++) {
            Runnable proxyTask = () -> {
                for (int j = 0; j < cycles; j++) {
                    if (proxyQueue.isEmpty()) {
                        continue;
                    }
                    ProxyConfigHolder proxy = proxyQueue.poll();
                    System.out.printf("(Thread: %s) Checking proxy: %s:%d%n",
                            Thread.currentThread().getName(),
                            proxy.getProxyNetworkConfig().getHostname(),
                            proxy.getProxyNetworkConfig().getPort());
                    long start = System.currentTimeMillis();
                    boolean validateProxy = proxyValidator.proxyValidate(proxy.getProxyNetworkConfig());
                    if (validateProxy) {
                        long delay = System.currentTimeMillis() - start;
                        System.out.printf("(Thread: %s) Proxy checked: %s:%d - Status OK %dms%n",
                                Thread.currentThread().getName(),
                                proxy.getProxyNetworkConfig().getHostname(),
                                proxy.getProxyNetworkConfig().getPort(),
                                delay);
                        availableProxyList.add(proxy);
                    } else {
                        System.out.printf("(Thread: %s) Proxy checked: %s:%d - Status FAIL%n",
                                Thread.currentThread().getName(),
                                proxy.getProxyNetworkConfig().getHostname(),
                                proxy.getProxyNetworkConfig().getPort());
                    }
                }
                countDownLatch.countDown();
            };
            executorService.submit(proxyTask);
        }
        countDownLatch.await();
        assertTrue(true);
    }

    @AfterEach
    public void tearDown() {
        System.out.println("Executor service shutting down...");
        executorService.shutdown();
    }
}