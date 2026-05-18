package health;

import data.Entity;
import data.GameData;
import data.World;
import services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.List;

public class HealthSystem implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> deadEntities = new ArrayList<>();

        for (Entity entity : world.getEntities()) {
            if (!entity.isDamageable()) {
                continue;
            }

            if (entity.getPendingDamage() > 0) {
                entity.setHealth(entity.getHealth() - entity.getPendingDamage());
                entity.clearPendingDamage();

                System.out.println(entity.getClass().getSimpleName()
                        + " health: "
                        + entity.getHealth()
                        + "/"
                        + entity.getMaxHealth());
            }

            if (entity.getHealth() <= 0) {
                deadEntities.add(entity);
            }
        }

        for (Entity entity : deadEntities) {
            System.out.println(entity.getClass().getSimpleName() + " died");
            world.removeEntity(entity);
        }
    }
}