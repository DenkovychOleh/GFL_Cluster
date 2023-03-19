package executor.service.model;

import java.util.Objects;

public class ProxyNetworkConfig {
    private String hostname;
    private Integer port;

    public ProxyNetworkConfig() {
    }

    public ProxyNetworkConfig(String hostname, Integer port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) return true;
        if (!(o instanceof ProxyNetworkConfig)) {
            return false;
        }
        ProxyNetworkConfig proxyNetworkConfig = (ProxyNetworkConfig) o;
        return hostname.equals(proxyNetworkConfig.hostname) &&
                Objects.equals(port, proxyNetworkConfig.port);
    }


    @Override
    public int hashCode() {
        return Objects.hash(hostname, port);
    }
}