import commonbullet.BulletSPI;
import services.IEntityProcessingService;

module Bullet {
    requires Common;
    requires CommonBullet;

    provides BulletSPI with bullet.BulletControl;
    provides IEntityProcessingService with bullet.BulletControl;
}
