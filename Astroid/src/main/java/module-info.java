import services.IEntityProcessingService;
import services.IGamePluginService;

module Astroid {

    requires Common;

    provides IGamePluginService
            with asteroid.AsteroidPlugin;

    provides IEntityProcessingService
            with asteroid.AsteroidControlSystem;
}