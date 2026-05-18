import services.IPostEntityProcessingService;

module Collision {
    requires Common;
    requires spring.web;

    provides IPostEntityProcessingService
            with collision.Collision;
}