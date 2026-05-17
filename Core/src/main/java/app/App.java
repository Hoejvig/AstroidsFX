package app;

import javafx.application.Application;
import javafx.stage.Stage;
import servicelocator.ServiceLocator;
import services.IEntityProcessingService;
import services.IGamePluginService;
import services.IPostEntityProcessingService;

import org.springframework.context.support.GenericApplicationContext;

import data.GameData;
import data.World;

public class App extends Application {

    private GenericApplicationContext context;
    private Game game;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        context = new GenericApplicationContext();

        context.registerBean(GameData.class);
        context.registerBean(World.class);

        registerServices(context, IGamePluginService.class);
        registerServices(context, IEntityProcessingService.class);
        registerServices(context, IPostEntityProcessingService.class);

        context.registerBean(Game.class);

        context.refresh();

        game = context.getBean(Game.class);
        game.start(stage);
    }

    @Override
    public void stop() {
        if (game != null) {
            game.stop();
        }

        if (context != null) {
            context.close();
        }
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private <T> void registerServices(GenericApplicationContext context, Class<T> serviceType) {
        int index = 0;

        for (T service : ServiceLocator.INSTANCE.locateAll(serviceType)) {
            T serviceInstance = service;

            context.registerBean(
                    serviceType.getSimpleName() + "_" + index,
                    (Class) serviceInstance.getClass(),
                    () -> serviceInstance
            );

            index++;
        }
    }
}