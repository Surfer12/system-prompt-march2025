// src/main/java/com/uplift/system/library/Librarian.java
package com.uplift.system.library;

import com.uplift.system.gate.Connector;
import com.uplift.system.gate.ConnectorFactory;
import com.uplift.system.adapters.Adapter;
import com.uplift.system.adapters.AdapterFactory;
import org.yaml.snakeyaml.Yaml;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class Librarian {
    private static final Logger logger = Logger.getLogger(Librarian.class.getName());
    private Map<String, Connector> connectors;
    private Map<String, Adapter> adapters;
    private static final String DEFAULT_REGISTRY_PATH = "config/components.yaml";

    public Librarian() {
        this(DEFAULT_REGISTRY_PATH);
    }

    public Librarian(String configPath) {
        this.connectors = new HashMap<>();
        this.adapters = new HashMap<>();
        loadConfiguration(configPath);
    }

    private void loadConfiguration(String configPath) {
        try (InputStream inputStream = new FileInputStream(configPath)) {
            Yaml yaml = new Yaml();
            Map<String, List<Map<String, String>>> config = yaml.load(inputStream);

            if (config != null) {
                loadConnectors(config.get("connectors"));
                loadAdapters(config.get("adapters"));
            } else {
                logger.warning("Configuration file is empty or invalid.");
            }
        } catch (FileNotFoundException e) {
            logger.severe("Configuration file not found at " + configPath + ". Using default configuration.");
        } catch (Exception e) {
            logger.severe("Error loading configuration: " + e.getMessage());
        }
    }

    private void loadConnectors(List<Map<String, String>> connectorConfigs) {
        if (connectorConfigs != null) {
            for (Map<String, String> config : connectorConfigs) {
                String technology = config.get("technology");
                if (technology != null) {
                    Connector connector = ConnectorFactory.getConnector(technology);
                    if (connector != null) {
                        connectors.put(technology, connector);
                        logger.info("Loaded connector for " + technology);
                    } else {
                        logger.warning("Could not load connector for " + technology);
                    }
                }
            }
        }
    }

    private void loadAdapters(List<Map<String, String>> adapterConfigs) {
        if (adapterConfigs != null) {
            for (Map<String, String> config : adapterConfigs) {
                String technology = config.get("technology");
                if (technology != null) {
                    Adapter adapter = AdapterFactory.getAdapter(technology);
                    if (adapter != null) {
                        adapters.put(technology, adapter);
                        logger.info("Loaded adapter for " + technology);
                    } else {
                        logger.warning("Could not load adapter for " + technology);
                    }
                }
            }
        }
    }

    public Connector getConnector(String technology) {
        return connectors.get(technology);
    }

    public Adapter getAdapter(String technology) {
        return adapters.get(technology);
    }

    // Existing methods (tellStory, getComponentDetails, etc.) can remain as is,
    // but might need adjustments to use the loaded connectors and adapters.
    public String tellStory(String context) {
        StringBuilder storyBuilder = new StringBuilder("In the library of technologies, where gates connect different realms...\n");

        if (context != null && !context.isEmpty()) {
            storyBuilder.append("Today's tale revolves around: ").append(context).append("\n");
        }

        storyBuilder.append("Generated on: ").append(java.time.LocalDateTime.now()).append("\n");
        storyBuilder.append(generateRegistryBasedNarrative());

        return storyBuilder.toString();
    }

    private String generateRegistryBasedNarrative() {
        StringBuilder narrativeBuilder = new StringBuilder();
        connectors.forEach((technology, connector) -> {
            narrativeBuilder.append(String.format("The %s connector is ready.\n", technology));
        });
        adapters.forEach((technology, adapter) -> {
            narrativeBuilder.append(String.format("The %s adapter is ready.\n", technology));
        });
        return narrativeBuilder.toString();
    }

    public String tellStory() {
        return tellStory(null);
    }
}