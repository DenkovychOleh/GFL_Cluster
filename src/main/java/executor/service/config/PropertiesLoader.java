package executor.service.config;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;

public class PropertiesLoader implements ConfigurationLoader {

    private static final String PROPERTIES_FILE_NAME = "webDriver.properties";
    private static volatile PropertiesLoader instance;
    private Configuration configuration;

    private PropertiesLoader() {
        try {
            configuration = configurationBuilder().getConfiguration();
        } catch (ConfigurationException e) {
            e.printStackTrace();
        }
    }

    public static PropertiesLoader getInstance() {
        synchronized (PropertiesLoader.class) {
            if (instance == null) {
                instance = new PropertiesLoader();
            }
            return instance;
        }
    }

    @Override
    public Configuration getConfiguration() {
        return configuration;
    }

    private FileBasedConfigurationBuilder<FileBasedConfiguration> configurationBuilder() {
        return new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
                .configure(new Parameters().properties().setFileName(PROPERTIES_FILE_NAME));
    }

}
