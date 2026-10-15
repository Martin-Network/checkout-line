# Checkout Line

A small simulation of supermarket checkout lines, written in Java with [Processing](https://processing.org).

## Run

Requires JDK 27. If it is not installed, Gradle downloads it automatically.

Open the folder in IntelliJ IDEA (it imports the Gradle project) and run `checkout.App`, or use the terminal:

```
./gradlew run
./gradlew run --args="--seed=7 --speed=2"
```

## Controls

| Key | Action |
|---|---|
| Space | Pause / resume |
| → | One tick forward (while paused) |
| ↑ / ↓ | Faster / slower (ticks per frame) |
| R | Restart with the same seed |
| N | Restart with a new random seed |
| E | Start / stop recording frames |
| S | Screenshot |
| H | Show / hide the overlay |

The overlay in the top-left corner only exists in the window. It never appears in recorded frames or screenshots.

## Recording frames

Press `E`, or record a fixed number of frames without touching anything:

```
./gradlew run --args="--seed=42 --export=600"
```

Every frame is saved as a PNG in `frames/<scene>_<date>_<time>/`. Turn a recording into a video:

```
ffmpeg -framerate 60 -i frames/<folder>/%05d.png -c:v libx264 -pix_fmt yuv420p -crf 16 <folder>.mp4
```

Simulation time is counted in frames, not in seconds, so recordings are always perfectly smooth, even if the computer is too slow to show the simulation live.

For 4K output, set `Config.VIDEO_SCALE = 2`. Scenes keep drawing in 1920×1080 units.

## Without a window

```
./gradlew runHeadless --args="42 43200"
```

Runs the simulation as fast as possible (seed, number of ticks).

## Structure

```
src/main/java/checkout/
  App.java          window, controls, overlay, recording
  Config.java       video size, fps, default seed
  Headless.java     simulation without a window
  sim/              the supermarket itself: pure Java, no Processing
  render/           colours, frame export
  scenes/           what is drawn on screen
```

## Rules

- `sim` never imports Processing. The world must work without graphics.
- Scenes only read the simulation and draw it. They never change it.
- Same seed, same world.

## Renderer

The canvas uses Processing's default renderer (JAVA2D). If drawing gets slow with many objects, switch the window and the canvas to `P2D` (OpenGL): `size(..., P2D)` in `settings()` and `createGraphics(..., P2D)` in `setup()`.

## Contributing

See [CONTRIBUTING.md](CONTRIBUTING.md). Commit messages follow [Conventional Commits](https://www.conventionalcommits.org/en/v1.0.0/).

## License

[MIT](LICENSE). Processing core, which this project uses as a library, is licensed under the LGPL 2.1.
