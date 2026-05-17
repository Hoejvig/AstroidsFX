package asteroid;

import data.Entity;
import data.GameData;
import data.World;
import services.IEntityProcessingService;

public class AsteroidControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {

        for (Entity asteroid : world.getEntities(Asteroid.class)) {

            asteroid.setX(asteroid.getX() + asteroid.getDX());
            asteroid.setY(asteroid.getY() + asteroid.getDY());

            wrap(asteroid, gameData);
        }
    }

    private void wrap(Entity entity, GameData gameData) {

        if (entity.getX() < 0) {
            entity.setX(gameData.getDisplayWidth());
        }

        if (entity.getX() > gameData.getDisplayWidth()) {
            entity.setX(0);
        }

        if (entity.getY() < 0) {
            entity.setY(gameData.getDisplayHeight());
        }

        if (entity.getY() > gameData.getDisplayHeight()) {
            entity.setY(0);
        }
    }
}