# ExpandAbility
Minecraft library mod that provides increased control over vanilla abilities.

https://www.curseforge.com/minecraft/mc-mods/expandability

## Usage
Add the following to your `build.gradle` and replace `[version]` with the latest version [here](https://github.com/florensie/ExpandAbility/releases):

```groovy
repositories {
  maven { url = "https://maven.florens.be/releases" }
}

dependencies {
  // For Fabric
  modImplementation include("be.florens:expandability-fabric:[version]")
  
  // For Forge
  implementation fg.deobf("be.florens:expandability-forge:[version]")
  
  // For Architectury common
  modImplementation include("be.florens:expandability:[version]")
}
```
If you're using ForgeGradle you might also need this to fix Mixin's broken refmap remapping: https://github.com/SpongePowered/Mixin/issues/462#issuecomment-791370319

Some example usages of the API can be found in the `testmod-[platform]` directories of this repository.

## Tests
Tests are located in the fabric-testmod module.

## To Do
### Swimming ability
  - Test with modded fluids
  - Test with elytra & Caelus API

### Fluid walking ability
  - Fix no fall damage when falling on water
  - Fix sneaking on water

### Other
- Persistent status effects
- Add commands for testing
- LivingEntity scaling
