package bullet;

import data.Entity;
import data.GameData;
import data.World;
import commonbullet.Bullet;
import commonbullet.BulletSPI;
import services.IEntityProcessingService;

public class BulletControl implements IEntityProcessingService, BulletSPI {

    @Override
    public void process(GameData gameData, World world) {
        for (Entity bullet : world.getEntities(Bullet.class)) {
            double changeX = Math.cos(Math.toRadians(bullet.getRotation()));
            double changeY = Math.sin(Math.toRadians(bullet.getRotation()));

            bullet.setX(bullet.getX() + changeX * 3);
            bullet.setY(bullet.getY() + changeY * 3);
            

            if (bullet.getX() < 0 || bullet.getX() > gameData.getDisplayWidth()
                    || bullet.getY() < 0 || bullet.getY() > gameData.getDisplayHeight()) {
                world.removeEntity(bullet);
            }
        }
    }

    @Override
    public Entity createBullet(Entity shooter, GameData gameData) {
        Entity bullet = new Bullet();

        bullet.setPolygonCoordinates(1, -1, 1, 1, -1, 1, -1, -1);

        double changeX = Math.cos(Math.toRadians(shooter.getRotation()));
        double changeY = Math.sin(Math.toRadians(shooter.getRotation()));

        bullet.setX(shooter.getX() + changeX * (shooter.getRadius() + 8));
        bullet.setY(shooter.getY() + changeY * (shooter.getRadius() + 8));
        bullet.setRotation(shooter.getRotation());
        bullet.setRadius(1);

        return bullet;
    }
}