import services.IEntityProcessingService;
import services.IGamePluginService;

module Enemy {
    requires Common;
    requires CommonBullet;

    uses commonbullet.BulletSPI;

    provides IGamePluginService
            with enemy.EnemyPlugin;

    provides IEntityProcessingService
            with enemy.EnemyControlSystem;
}