package dk.sdu.cbse;

import dk.sdu.cbse.common.data.Entity;
import dk.sdu.cbse.common.data.GameData;
import dk.sdu.cbse.common.data.GameKeys;
import dk.sdu.cbse.common.data.World;
import dk.sdu.cbse.common.services.IEntityProcessingService;
import dk.sdu.cbse.common.services.IGamePluginService;
import dk.sdu.cbse.common.services.IPostEntityProcessingService;

import dk.sdu.cbse.common.util.ServiceLocator;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {

    private final GameData gameData = new GameData();
    private final World world = new World();

    private GraphicsContext graphicsContext;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) {
        Canvas canvas = new Canvas(
                gameData.getDisplayWidth(),
                gameData.getDisplayHeight()
        );

        graphicsContext = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new StackPane(canvas));

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                gameData.getKeys().setKey(GameKeys.LEFT, true);
            }
            if (event.getCode() == KeyCode.RIGHT) {
                gameData.getKeys().setKey(GameKeys.RIGHT, true);
            }
            if (event.getCode() == KeyCode.UP) {
                gameData.getKeys().setKey(GameKeys.UP, true);
            }
            if (event.getCode() == KeyCode.SPACE) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
            }
            if (event.getCode() == KeyCode.DOWN) {
                gameData.getKeys().setKey(GameKeys.DOWN, true);
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode() == KeyCode.LEFT) {
                gameData.getKeys().setKey(GameKeys.LEFT, false);
            }
            if (event.getCode() == KeyCode.RIGHT) {
                gameData.getKeys().setKey(GameKeys.RIGHT, false);
            }
            if (event.getCode() == KeyCode.UP) {
                gameData.getKeys().setKey(GameKeys.UP, false);
            }
            if (event.getCode() == KeyCode.SPACE) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
            if (event.getCode() == KeyCode.DOWN) {
                gameData.getKeys().setKey(GameKeys.DOWN, false);
            }
        });

        for (IGamePluginService plugin : ServiceLocator.INSTANCE.locateAll(IGamePluginService.class)) {
            plugin.start(gameData, world);
        }

        new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
                gameData.getKeys().update();
            }
        }.start();

        window.setTitle("AsteroidsFX");
        window.setScene(scene);
        window.show();
    }

    private void update() {
    for (IEntityProcessingService processor : ServiceLocator.INSTANCE.locateAll(IEntityProcessingService.class)) {
        processor.process(gameData, world);
    }

    for (IPostEntityProcessingService postProcessor : ServiceLocator.INSTANCE.locateAll(IPostEntityProcessingService.class)) {
        postProcessor.process(gameData, world);
    }
    }

    private void render() {
        graphicsContext.clearRect(
                0,
                0,
                gameData.getDisplayWidth(),
                gameData.getDisplayHeight()
        );

        for (Entity entity : world.getEntities()) {
            drawEntity(entity);
        }
    }

    private void drawEntity(Entity entity) {
        double[] coordinates = entity.getPolygonCoordinates();

        if (coordinates == null || coordinates.length < 4) {
            return;
        }

        double[] xPoints = new double[coordinates.length / 2];
        double[] yPoints = new double[coordinates.length / 2];

        for (int i = 0; i < coordinates.length; i += 2) {
            double localX = coordinates[i];
            double localY = coordinates[i + 1];

            double rotation = Math.toRadians(entity.getRotation());

            double rotatedX = localX * Math.cos(rotation) - localY * Math.sin(rotation);
            double rotatedY = localX * Math.sin(rotation) + localY * Math.cos(rotation);

            xPoints[i / 2] = rotatedX + entity.getX();
            yPoints[i / 2] = rotatedY + entity.getY();
        }

        graphicsContext.strokePolygon(xPoints, yPoints, xPoints.length);
    }
}