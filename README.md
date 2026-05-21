# AsteroidsFX

AsteroidsFX is a component-based JavaFX game inspired by the classic Asteroids arcade game.  
The game is built with Maven, JPMS modules, Java ServiceLoader, a plugin folder, Spring dependency injection in Core, and a separate Spring Boot scoring microservice.

## Features

- Player spaceship
- Enemy spaceship
- Asteroids
- Bullets / weapon system
- Collision system
- Health system
- Game over screen
- Score system through a Spring Boot microservice
- Runtime plugin loading using JPMS `ModuleLayer` and `ServiceLoader`

## Controls

| Key | Action |
| `LEFT ARROW` | Rotate player left |
| `RIGHT ARROW` | Rotate player right |
| `UP ARROW` | Move forward |
| `DOWN ARROW` | Move backward |
| `SPACE` | Shoot |

## Project Structure

AstroidsFX/
├── Core/              # JavaFX window, rendering, input, game loop, Spring DI
├── Common/            # Shared Entity, World, GameData and service interfaces
├── CommonBullet/      # BulletSPI and BulletServiceLocator
├── Player/            # Player plugin
├── Bullet/            # Bullet plugin and BulletSPI provider
├── Astroid/           # Asteroid plugin
├── Enemy/             # Enemy plugin
├── Collision/         # Collision plugin
├── Health/            # Health plugin
├── ScoringService/    # Separate Spring Boot scoring microservice
├── mods-mvn/          # Runtime folder for Core/Common/CommonBullet jars
├── plugins/           # Runtime folder for gameplay plugin jars
└── libs/              # JavaFX, Spring and Micrometer runtime jars
