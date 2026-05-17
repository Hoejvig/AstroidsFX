package commonbullet;

import data.Entity;
import data.GameData;

public interface BulletSPI {
    Entity createBullet(Entity shooter, GameData gameData);
}
