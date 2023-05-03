package executor.service.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.Resources;
import executor.service.model.ProxyConfigHolder;
import executor.service.model.ProxyCredentials;
import executor.service.model.ProxyNetworkConfig;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProxySourcesClientImpl implements ProxySourcesClient {
    @Value(value = "${url.proxy}")
    private String url;
    private List<ProxyNetworkConfig> proxyNetworkConfigs;
    private List<ProxyCredentials> proxyCredentials;

    public void setProxyNetworkConfigs(List<ProxyNetworkConfig> proxyNetworkConfigs) {
        this.proxyNetworkConfigs = proxyNetworkConfigs;
    }
    public void setProxyCredentials(List<ProxyCredentials> proxyCredentials) {
        this.proxyCredentials = proxyCredentials;
    }
    @Override
    public ProxyConfigHolder getProxy(){
        OkHttpClient client = new OkHttpClient();
        ObjectMapper objectMapper = new ObjectMapper();
        Request request = new Request.Builder()
                .url(url)
                .build();
        try (Response response = client.newCall(request).execute()){
            return  objectMapper.readValue(response.body().string(), ProxyConfigHolder.class);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public List<ProxyConfigHolder> getListProxy() {
        List <ProxyConfigHolder> tmp = new ArrayList<>();
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
    @Override
    public List <ProxyConfigHolder> getProxy(String fileProxyCredentials, String fileProxyNetworkConfig) throws IOException {

        ObjectMapper objectMapper = new ObjectMapper();
        InputStream inputStreamProxyNetwork = Resources.getResource(fileProxyNetworkConfig).openStream();
        setProxyNetworkConfigs(objectMapper.readValue(inputStreamProxyNetwork,
                new TypeReference<ArrayList<ProxyNetworkConfig>>() {
        }));
        inputStreamProxyNetwork.close();

        InputStream inputStreamProxyCredentials = Resources.getResource(fileProxyCredentials).openStream();
        setProxyCredentials(objectMapper.readValue(inputStreamProxyCredentials,
                new TypeReference<ArrayList<ProxyCredentials>>() {
        }));
        inputStreamProxyCredentials.close();

        return getListProxy();
    }
}
