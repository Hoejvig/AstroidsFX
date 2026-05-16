import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;

module Astroid {

    requires Common;

    provides IGamePluginService
            with dk.sdu.cbse.asteroid.AsteroidPlugin;

    provides IEntityProcessingService
            with dk.sdu.cbse.asteroid.AsteroidControlSystem;
}