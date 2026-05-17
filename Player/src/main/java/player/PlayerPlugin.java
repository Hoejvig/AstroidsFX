package player;

import data.Entity;
import data.GameData;
import data.World;
import services.IGamePluginService;

public class PlayerPlugin implements IGamePluginService {

    private Entity player;

    @Override
    public void start(GameData gameData, World world) {
        player = createPlayerShip(gameData);
        world.addEntity(player);
    }

    private Entity createPlayerShip(GameData gameData) {
        Entity playerShip = new Player();

        playerShip.setPolygonCoordinates(
            14, 0,      // nose
            2, -4,      // upper front body
            -5, -12,    // upper wing
            -3, -4,     // upper rear body
            -10, -6,    // upper tail
            -6, 0,      // back center
            -10, 6,     // lower tail
            -3, 4,      // lower rear body
            -5, 12,     // lower wing
            2, 4        // lower front body
    );

        playerShip.setX(gameData.getDisplayWidth() / 2);
        playerShip.setY(gameData.getDisplayHeight() / 2);
        playerShip.setRadius(14);

        return playerShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(player);
    }
}