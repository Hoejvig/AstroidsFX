package collision;

import data.Entity;
import data.GameData;
import data.World;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CollisionTest {

    @Test
    public void shouldRemoveEntitiesWhenTheyCollide() {
        // Arrange
        World world = new World();
        GameData gameData = new GameData();

        Entity entity1 = new Entity();
        entity1.setX(100);
        entity1.setY(100);
        entity1.setRadius(10);

        Entity entity2 = new Entity();
        entity2.setX(105);
        entity2.setY(100);
        entity2.setRadius(10);

        world.addEntity(entity1);
        world.addEntity(entity2);

        Collision collision = new Collision();

        // Act
        collision.process(gameData, world);

        // Assert
        assertEquals(0, world.getEntities().size());
    }

    @Test
    public void shouldNotRemoveEntitiesWhenTheyDoNotCollide() {
        // Arrange
        World world = new World();
        GameData gameData = new GameData();

        Entity entity1 = new Entity();
        entity1.setX(100);
        entity1.setY(100);
        entity1.setRadius(10);

        Entity entity2 = new Entity();
        entity2.setX(300);
        entity2.setY(300);
        entity2.setRadius(10);

        world.addEntity(entity1);
        world.addEntity(entity2);

        Collision collision = new Collision();

        // Act
        collision.process(gameData, world);

        // Assert
        assertEquals(2, world.getEntities().size());
    }
}