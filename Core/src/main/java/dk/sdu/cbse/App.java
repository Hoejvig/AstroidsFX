package dk.sdu.cbse;

import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.util.ServiceLocator;
public class App { public static void main(String[] args) {
        GameData gameData = new GameData();
        World world = new World();

        System.out.println("Starting Core...");

        for (IGamePluginService plugin : ServiceLocator.INSTANCE.locateAll(IGamePluginService.class)) {
            System.out.println("Starting plugin: " + plugin.getClass().getName());
            plugin.start(gameData, world);
        }

        for (int i = 0; i < 5; i++) {
            for (IEntityProcessingService processor : ServiceLocator.INSTANCE.locateAll(IEntityProcessingService.class)) {
                System.out.println("Processing: " + processor.getClass().getName());
                processor.process(gameData, world);
            }

            System.out.println("Entities: " + world.getEntities().size());
        }
    }
    }
