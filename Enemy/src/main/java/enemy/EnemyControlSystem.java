package enemy;

import commonbullet.BulletSPI;
import commonbullet.BulletServiceLocator;
import data.Entity;
import data.GameData;
import data.World;
import services.IEntityProcessingService;

import java.util.Random;


public class EnemyControlSystem implements IEntityProcessingService {

    private final Random random = new Random();
    private int shootCooldown = 0;

    @Override
    public void process(GameData gameData, World world) {
        for (Entity enemy : world.getEntities(Enemy.class)) {
            moveRandomly(enemy, gameData);
            rotateTowardsPlayer(enemy, world);
            shootAtPlayer(enemy, gameData, world);
        }
    }

    private void moveRandomly(Entity enemy, GameData gameData) {
        if (random.nextInt(60) == 0) {
            enemy.setRotation(random.nextInt(360));
        }

        double changeX = Math.cos(Math.toRadians(enemy.getRotation()));
        double changeY = Math.sin(Math.toRadians(enemy.getRotation()));

        enemy.setX(enemy.getX() + changeX * 0.8);
        enemy.setY(enemy.getY() + changeY * 0.8);

        wrap(enemy, gameData);
    }

    private void shootAtPlayer(Entity enemy, GameData gameData, World world) {
        if (shootCooldown > 0) {
            shootCooldown--;
            return;
        }

        for (BulletSPI bulletSPI : BulletServiceLocator.locateAll()) {
            world.addEntity(bulletSPI.createBullet(enemy, gameData));
            shootCooldown = 90;
            break;
        }
    }

    private void rotateTowardsPlayer(Entity enemy, World world) {
        for (Entity entity : world.getEntities()) {
            if (entity.getClass().getSimpleName().equals("Player")) {
                double dx = entity.getX() - enemy.getX();
                double dy = entity.getY() - enemy.getY();

                enemy.setRotation(Math.toDegrees(Math.atan2(dy, dx)));
                return;
            }
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