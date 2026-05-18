package app;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import services.IEntityProcessingService;
import services.IGamePluginService;
import services.IPostEntityProcessingService;

import java.util.List;

import data.Entity;
import data.GameData;
import data.GameKeys;
import data.World;

public class Game {

    private final ScoreClient scoreClient;
    private int cachedScore = 0;
    private int scoreRefreshCounter = 0;

    private final GameData gameData;
    private final World world;

    private final List<IGamePluginService> gamePlugins;
    private final List<IEntityProcessingService> entityProcessors;
    private final List<IPostEntityProcessingService> postEntityProcessors;

    private GraphicsContext graphicsContext;
    private AnimationTimer gameLoop;

    public Game(
            GameData gameData,
            World world,
            List<IGamePluginService> gamePlugins,
            List<IEntityProcessingService> entityProcessors,
            List<IPostEntityProcessingService> postEntityProcessors,
            ScoreClient scoreClient
    ) {
        this.gameData = gameData;
        this.world = world;
        this.gamePlugins = gamePlugins;
        this.entityProcessors = entityProcessors;
        this.postEntityProcessors = postEntityProcessors;
        this.scoreClient = scoreClient;
    }

    public void start(Stage stage) {
        Canvas canvas = new Canvas(
                gameData.getDisplayWidth(),
                gameData.getDisplayHeight()
        );

        

        graphicsContext = canvas.getGraphicsContext2D();

        Scene scene = new Scene(new StackPane(canvas));
        setupInput(scene);

        for (IGamePluginService plugin : gamePlugins) {
            plugin.start(gameData, world);
        }

        gameLoop = new AnimationTimer() {
            @Override
            public void handle(long now) {
                update();
                render();
                gameData.getKeys().update();
            }
        };

        gameLoop.start();

        stage.setTitle("AsteroidsFX - Spring Core");
        stage.setScene(scene);
        stage.show();

        scoreClient.reset();



    }

    public void stop() {
        if (gameLoop != null) {
            gameLoop.stop();
        }

        for (IGamePluginService plugin : gamePlugins) {
            plugin.stop(gameData, world);
        }
    }

    private void update() {
        for (IEntityProcessingService processor : entityProcessors) {
            processor.process(gameData, world);
        }

        for (IPostEntityProcessingService processor : postEntityProcessors) {
            processor.process(gameData, world);
        }

        scoreRefreshCounter++;

        if(scoreRefreshCounter >= 30) {
            int currentScore = scoreClient.getScore();
            if (currentScore != cachedScore) {
                cachedScore = currentScore;
                System.out.println("Current Score: " + cachedScore);
            }
            scoreRefreshCounter = 0;
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

        graphicsContext.fillText("Score: " + cachedScore, 10, 20);
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

    private void setupInput(Scene scene) {
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

            if (event.getCode() == KeyCode.DOWN) {
                gameData.getKeys().setKey(GameKeys.DOWN, true);
            }

            if (event.getCode() == KeyCode.SPACE) {
                gameData.getKeys().setKey(GameKeys.SPACE, true);
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

            if (event.getCode() == KeyCode.DOWN) {
                gameData.getKeys().setKey(GameKeys.DOWN, false);
            }

            if (event.getCode() == KeyCode.SPACE) {
                gameData.getKeys().setKey(GameKeys.SPACE, false);
            }
        });
    }
}