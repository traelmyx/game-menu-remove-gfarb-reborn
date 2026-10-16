# Game Menu Remove GFARB Reborn

Removes the "Give Feedback" and "Report Bugs" buttons from the pause menu. The rest of the menu closes up, so there's no gap where they were. On a server that sends links, the "Feedback..." button goes too and "Server Links" takes the whole row.

Port of [Game Menu Remove GFARB](https://github.com/MORIMORI0317/GameMenuRemoveGFARB) by MORIMORI0317 to Minecraft 1.21.1, for NeoForge and Fabric. Client only.

With Mod Menu installed (Fabric), the two buttons are hidden instead of removed, because Mod Menu places its Mods button next to them.

## Building

`./gradlew buildAll` builds both loaders. The client smoke test opens the pause menu and checks the buttons are gone.

## License

LGPL-3.0, same as the original. See [LICENSE](LICENSE).
