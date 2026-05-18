package servicelocator;

import java.lang.module.Configuration;
import java.lang.module.ModuleDescriptor;
import java.lang.module.ModuleFinder;
import java.lang.module.ModuleReference;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ServiceConfigurationError;
import java.util.ServiceLoader;
import java.util.stream.Collectors;

public enum ServiceLocator {

    INSTANCE;

    private static final Map<Class<?>, ServiceLoader<?>> LOADERS = new HashMap<>();

    private final ModuleLayer layer;

    ServiceLocator() {
        this.layer = createPluginLayer();
    }

    public ModuleLayer getLayer() {
        return layer;
    }

    private ModuleLayer createPluginLayer() {
        try {
            Path pluginsDir = Paths.get("plugins");

            if (!Files.exists(pluginsDir)) {
                Files.createDirectories(pluginsDir);
            }

            ModuleFinder pluginFinder = ModuleFinder.of(pluginsDir);

            List<String> pluginModuleNames = pluginFinder
                    .findAll()
                    .stream()
                    .map(ModuleReference::descriptor)
                    .map(ModuleDescriptor::name)
                    .collect(Collectors.toList());

            Configuration pluginConfiguration = ModuleLayer
                    .boot()
                    .configuration()
                    .resolve(pluginFinder, ModuleFinder.of(), pluginModuleNames);

            return ModuleLayer
                    .boot()
                    .defineModulesWithOneLoader(
                            pluginConfiguration,
                            ClassLoader.getSystemClassLoader()
                    );
        } catch (Exception exception) {
            throw new IllegalStateException("Could not create plugin module layer", exception);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> locateAll(Class<T> service) {
        ServiceLoader<T> loader = (ServiceLoader<T>) LOADERS.get(service);

        if (loader == null) {
            loader = ServiceLoader.load(layer, service);
            LOADERS.put(service, loader);
        }

        List<T> services = new ArrayList<>();

        try {
            for (T serviceInstance : loader) {
                services.add(serviceInstance);
            }
        } catch (ServiceConfigurationError error) {
            error.printStackTrace();
        }

        return services;
    }
}