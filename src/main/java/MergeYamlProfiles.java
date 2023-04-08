import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class MergeYamlProfiles {

    public static final String APPLICATION_YML = "application.yml";
    public static final String APPLICATION_DEV_YML = "application-dev.yml";
    public static final String MERGED_CONFIG_YML = "src/main/resources/merged-config.yml";

    public static void main(String[] args) {
        DumperOptions options = new DumperOptions();
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        Yaml yaml = new Yaml(options);

        Map<String, Object> defaultConfig = loadYaml(APPLICATION_YML, yaml);
        Map<String, Object> devConfig = loadYaml(APPLICATION_DEV_YML, yaml);

        // Merge dev config into default config
        mergeConfigs(defaultConfig, devConfig);

        // Output merged config to file
        writeToFile(yaml, defaultConfig);
    }

    private static void writeToFile(Yaml yaml, Map<String, Object> defaultConfig) {
        try {
            FileWriter writer = new FileWriter(MERGED_CONFIG_YML);
            yaml.dump(defaultConfig, writer);
            writer.close();
            System.out.println("Merged configuration written to merged-config.yml");
        } catch (IOException e) {
            System.out.println("Error writing merged configuration to file: " + e.getMessage());
        }
    }

    private static Map<String, Object> loadYaml(String fileName, Yaml yaml) {
        InputStream inputStream = MergeYamlProfiles.class.getClassLoader().getResourceAsStream(fileName);
        return yaml.load(inputStream);
    }

    @SuppressWarnings("unchecked")
    private static void mergeConfigs(Map<String, Object> defaultConfig, Map<String, Object> devConfig) {
        for (String key : devConfig.keySet()) {

            Object devValue = devConfig.get(key);
            if (defaultConfig.containsKey(key)) {
                Object defaultValue = defaultConfig.get(key);

                if (defaultValue instanceof Map && devValue instanceof Map) {
                    // Recursive merge for nested maps
                    mergeConfigs((Map<String, Object>) defaultValue, (Map<String, Object>) devValue);
                } else {
                    // Override default value with dev value
                    defaultConfig.put(key, devValue);
                }
            } else {
                // Add new key-value pair to default config
                defaultConfig.put(key, devValue);
            }
        }
    }
}
