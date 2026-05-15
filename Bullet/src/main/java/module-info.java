import dk.sdu.cbse.common.bullet.BulletSPI;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
module Bullet {
    requires Common;
    requires CommonBullet;

    provides IEntityProcessingService
            with dk.sdu.cbse.bullet.BulletControl;

    provides BulletSPI
            with dk.sdu.cbse.bullet.BulletControl;
    
}
