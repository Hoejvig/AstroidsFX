module Common {
    exports data;
    exports services;
    exports servicelocator;

    uses services.IGamePluginService;
    uses services.IEntityProcessingService;
    uses services.IPostEntityProcessingService;
}