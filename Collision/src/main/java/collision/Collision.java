package collision;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.List;

public class Collision implements IPostEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());
        List<Entity> toRemove = new ArrayList<>();

        for (int i = 0; i < entities.size(); i++) {
            Entity entity1 = entities.get(i);

            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity2 = entities.get(j);

                if (collides(entity1, entity2)) {
                    toRemove.add(entity1);
                    toRemove.add(entity2);
                }
            }
        }

        for (Entity entity : toRemove) {
            world.removeEntity(entity);
        }
    }

    private boolean collides(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();
        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < entity1.getRadius() + entity2.getRadius();
    }
}