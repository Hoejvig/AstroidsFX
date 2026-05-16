package dk.sdu.cbse.player;

import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;

import java.util.ServiceLoader;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {
            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 5);
            }

            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 5);
            }

            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                double speed = 3.5;
                player.setX(player.getX() + changeX * speed);
                player.setY(player.getY() + changeY * speed);
            }

            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                for (BulletSPI bulletSPI : ServiceLoader.load(BulletSPI.class)) {
                    world.addEntity(bulletSPI.createBullet(player, gameData));
                    break;
                }
            }

            if (gameData.getKeys().isDown(GameKeys.DOWN)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));
                double speed = 3.5;
                player.setX(player.getX() - changeX * speed);
                player.setY(player.getY() - changeY * speed);
            }

            wrapAroundScreen(player, gameData);
        }
    }

    private void wrapAroundScreen(Entity player, GameData gameData) {
        if (player.getX() < 0) {
            player.setX(gameData.getDisplayWidth());
        }

        if (player.getX() > gameData.getDisplayWidth()) {
            player.setX(0);
        }

        if (player.getY() < 0) {
            player.setY(gameData.getDisplayHeight());
        }

        if (player.getY() > gameData.getDisplayHeight()) {
            player.setY(0);
        }
    }
}