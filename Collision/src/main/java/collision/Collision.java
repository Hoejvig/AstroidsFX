package collision;

import data.Entity;
import data.GameData;
import data.World;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import services.IPostEntityProcessingService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Collision implements IPostEntityProcessingService {

    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public void process(GameData gameData, World world) {
        List<Entity> entities = new ArrayList<>(world.getEntities());
        Set<Entity> toRemove = new HashSet<>();

        for (int i = 0; i < entities.size(); i++) {
            Entity entity1 = entities.get(i);

            for (int j = i + 1; j < entities.size(); j++) {
                Entity entity2 = entities.get(j);

                if (shouldCollide(entity1, entity2) && collides(entity1, entity2)) {
                    System.out.println("Collision detected between "
                            + entity1.getClass().getSimpleName()
                            + " and "
                            + entity2.getClass().getSimpleName());

                    toRemove.add(entity1);
                    toRemove.add(entity2);

                    if (shouldGiveScore(entity1, entity2)) {
                        addScore(10);
                    }
                }
            }
        }

        for (Entity entity : toRemove) {
            world.removeEntity(entity);
        }
    }

    private boolean shouldCollide(Entity entity1, Entity entity2) {
        return isPair(entity1, entity2, "Bullet", "Asteroid")
                || isPair(entity1, entity2, "Bullet", "Enemy")
                || isPair(entity1, entity2, "Player", "Asteroid")
                || isPair(entity1, entity2, "Player", "Enemy");
    }

    private boolean shouldGiveScore(Entity entity1, Entity entity2) {
        return isPair(entity1, entity2, "Bullet", "Asteroid")
                || isPair(entity1, entity2, "Bullet", "Enemy");
    }

    private boolean isPair(Entity entity1, Entity entity2, String typeA, String typeB) {
        String name1 = entity1.getClass().getSimpleName();
        String name2 = entity2.getClass().getSimpleName();

        return (name1.equals(typeA) && name2.equals(typeB))
                || (name1.equals(typeB) && name2.equals(typeA));
    }

    private boolean collides(Entity entity1, Entity entity2) {
        double dx = entity1.getX() - entity2.getX();
        double dy = entity1.getY() - entity2.getY();

        double distance = Math.sqrt(dx * dx + dy * dy);

        return distance < entity1.getRadius() + entity2.getRadius();
    }

    private void addScore(int points) {
        try {
            String newScore = restTemplate.postForObject(
                    "http://localhost:8081/api/score/add/" + points,
                    null,
                    String.class
            );

            System.out.println("Score updated to: " + newScore);
        } catch (RestClientException exception) {
            System.out.println("Could not update score");
            exception.printStackTrace();
        }
    }
}