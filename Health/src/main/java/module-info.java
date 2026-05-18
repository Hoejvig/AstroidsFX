import services.IPostEntityProcessingService;

module Health {
    requires Common;

    provides IPostEntityProcessingService
            with health.HealthSystem;
}