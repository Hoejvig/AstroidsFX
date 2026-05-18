package asteroid;

import data.Entity;
import data.GameData;
import data.World;
import services.IGamePluginService;

import java.util.Random;

public class AsteroidPlugin implements IGamePluginService {

    private final Random random = new Random();

    @Override
    public void start(GameData gameData, World world) {
        for (int i = 0; i < 5; i++) {
            world.addEntity(createAsteroid(gameData));
        }
    }

    private Entity createAsteroid(GameData gameData) {
        Asteroid asteroid = new Asteroid();

        asteroid.setPolygonCoordinates(
                10, -8,
                8, 8,
                -8, 10,
                -10, -8,
                -2, -10
        );

        asteroid.setX(random.nextInt(gameData.getDisplayWidth()));
        asteroid.setY(random.nextInt(gameData.getDisplayHeight()));

        asteroid.setRadius(12);

        asteroid.setDX(random.nextDouble() * 2 - 1);
        asteroid.setDY(random.nextDouble() * 2 - 1);

        asteroid.setMaxHealth(1);
        asteroid.setHealth(1);
        asteroid.setDamageable(true);

        return asteroid;
    }

    @Override
    public void stop(GameData gameData, World world) {
        for (Entity asteroid : world.getEntities(Asteroid.class)) {
            world.removeEntity(asteroid);
        }
    }
}