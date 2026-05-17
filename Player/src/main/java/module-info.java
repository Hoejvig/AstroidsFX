import services.IEntityProcessingService;
import services.IGamePluginService;

module Player {
    requires Common;
    requires CommonBullet;

    uses commonbullet.BulletSPI;

    provides IGamePluginService
            with player.PlayerPlugin;

    provides IEntityProcessingService
            with player.PlayerControlSystem;
}