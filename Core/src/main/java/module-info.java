module Core {
    requires Common;
    requires javafx.controls;
    requires javafx.graphics;

    uses dk.sdu.cbse.common.services.IGamePluginService;
    uses dk.sdu.cbse.common.services.IEntityProcessingService;
    uses dk.sdu.cbse.common.services.IPostEntityProcessingService;

    opens dk.sdu.cbse to javafx.graphics;
}