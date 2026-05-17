module Core {
    requires Common;

    requires javafx.controls;
    requires javafx.graphics;

    requires spring.context;
    requires spring.beans;
    requires spring.core;

    uses services.IGamePluginService;
    uses services.IEntityProcessingService;
    uses services.IPostEntityProcessingService;

    opens app to javafx.graphics, spring.core, spring.beans, spring.context;
}