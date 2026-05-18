package player;

import commonbullet.BulletSPI;
import commonbullet.BulletServiceLocator;
import data.Entity;
import data.GameData;
import data.GameKeys;
import data.World;
import services.IEntityProcessingService;

public class PlayerControlSystem implements IEntityProcessingService {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity player : world.getEntities(Player.class)) {

            double speed = 3.5;

            if (gameData.getKeys().isDown(GameKeys.LEFT)) {
                player.setRotation(player.getRotation() - 5);
            }

            if (gameData.getKeys().isDown(GameKeys.RIGHT)) {
                player.setRotation(player.getRotation() + 5);
            }

            if (gameData.getKeys().isDown(GameKeys.UP)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));

                player.setX(player.getX() + changeX * speed);
                player.setY(player.getY() + changeY * speed);
            }

            if (gameData.getKeys().isDown(GameKeys.DOWN)) {
                double changeX = Math.cos(Math.toRadians(player.getRotation()));
                double changeY = Math.sin(Math.toRadians(player.getRotation()));

                player.setX(player.getX() - changeX * speed);
                player.setY(player.getY() - changeY * speed);
            }

            if (gameData.getKeys().isPressed(GameKeys.SPACE)) {
                System.out.println("SPACE pressed");

                int bulletProviders = 0;

                for (BulletSPI bulletSPI : BulletServiceLocator.locateAll()) {
                    bulletProviders++;
                    world.addEntity(bulletSPI.createBullet(player, gameData));
                    break;
                }

                System.out.println("BulletSPI providers found: " + bulletProviders);
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