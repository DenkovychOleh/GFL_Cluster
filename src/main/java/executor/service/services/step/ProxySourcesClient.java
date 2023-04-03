package executor.service.services.step;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class ProxySourcesClient implements ProxySources {
    private final ArrayList <ProxyConfigHolder> tmp = new ArrayList<>();
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public ArrayList <ProxyConfigHolder> getProxy(String fileProxyCredentials, String fileProxyNetworkConfig) throws IOException {

        InputStream inputStreamProxyNetwork = Resources.getResource(fileProxyNetworkConfig).openStream();
        ArrayList<ProxyNetworkConfig> proxyNetworkConfigs = objectMapper.readValue(inputStreamProxyNetwork,
                new TypeReference<ArrayList<ProxyNetworkConfig>>() {
                });
        inputStreamProxyNetwork.close();

        InputStream inputStreamProxyCredentials = Resources.getResource(fileProxyCredentials).openStream();
        ArrayList<ProxyCredentials> proxyCredentials = objectMapper.readValue(inputStreamProxyCredentials,
                new TypeReference<ArrayList<ProxyCredentials>>() {
                });
        inputStreamProxyCredentials.close();

        for (ProxyNetworkConfig proxyNetworkConfig : proxyNetworkConfigs) {

            for (ProxyCredentials proxyCredential : proxyCredentials) {

                ProxyConfigHolder proxyConfigHolder = new ProxyConfigHolder();
                proxyConfigHolder.setProxyNetworkConfig(proxyNetworkConfig);
                proxyConfigHolder.setProxyCredentials(proxyCredential);

                tmp.add(proxyConfigHolder);
            }
        }
        return tmp;
    }
}
