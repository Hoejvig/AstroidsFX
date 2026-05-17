package enemy;

import data.Entity;
import data.GameData;
import data.World;
import services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {

    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemy(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemy(GameData gameData) {
        Entity enemy = new Enemy();

        enemy.setPolygonCoordinates(
                12, 0,
                -8, -8,
                -4, 0,
                -8, 8
        );

        enemy.setX(100);
        enemy.setY(100);
        enemy.setRotation(0);
        enemy.setRadius(12);

        return enemy;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}