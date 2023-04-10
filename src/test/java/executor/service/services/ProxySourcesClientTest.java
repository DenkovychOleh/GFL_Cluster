package executor.service.services;

import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.util.ArrayList;

class ProxySourcesClientTest {
    private final ProxySourcesClient proxySourcesClient = new ProxySourcesClient();
    private ArrayList<ProxyConfigHolder> proxyConfigHolders;


    @BeforeEach
    public void setUp() throws IOException {
        proxyConfigHolders = proxySourcesClient.getProxy("ProxyCredentials.json",
                "ProxyNetworkConfig.json");
         }

    @Test
    public void testNotNull(){
        Assertions.assertNotNull(proxyConfigHolders);
    }

    @Test
    public void testArraySize(){
        Assertions.assertEquals( 12 , proxyConfigHolders.size());
    }

    @Test
    public void testReadFileAndDeserialize(){

        ArrayList<ProxyCredentials> proxyCredentialsArrayList = new ArrayList<>();

        proxyCredentialsArrayList.add(0, new ProxyCredentials("user1", "pass1"));
        proxyCredentialsArrayList.add(1, new ProxyCredentials("user2", "pass2"));
        proxyCredentialsArrayList.add(2, new ProxyCredentials("user3", "pass3"));
        proxyCredentialsArrayList.add(3, new ProxyCredentials("user4", "pass4"));

        ArrayList<ProxyNetworkConfig> proxyNetworkConfigArrayList = new ArrayList<>();

        proxyNetworkConfigArrayList.add(0, new ProxyNetworkConfig("host1", 8080));
        proxyNetworkConfigArrayList.add(1, new ProxyNetworkConfig("host2", 8088));
        proxyNetworkConfigArrayList.add(2, new ProxyNetworkConfig("host3", 8089));

        ArrayList <ProxyConfigHolder> expectedList = new ArrayList<>();
        for (ProxyNetworkConfig networkConfig : proxyNetworkConfigArrayList) {
            for (ProxyCredentials credentials : proxyCredentialsArrayList) {
                expectedList.add(
                        new ProxyConfigHolder(networkConfig, credentials));
            }
        }

        Assertions.assertEquals(expectedList, proxyConfigHolders);
    }

    @Test
    public void testNonExistentFile(){
        Assertions.assertThrows(IllegalArgumentException.class, ()-> proxySourcesClient.getProxy("NonExistentFile1.json",
                "NonExistentFile2.json"));
    }

    @Test
    public void testNotCorrectJsonFile(){
        Assertions.assertThrows(UnrecognizedPropertyException.class,
                ()-> proxySourcesClient.getProxy("ProxyNetworkConfig.json",
                        "ProxyCredentials.json"));
    }
}