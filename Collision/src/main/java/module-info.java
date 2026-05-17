import services.IPostEntityProcessingService;

module Collision {
    requires Common;

    provides IPostEntityProcessingService
            with collision.Collision;
}