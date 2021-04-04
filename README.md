# ExpandAbility
Minecraft library mod that provides increased control over vanilla abilities.

## Usage
Add the following to your `build.gradle`:

```groovy
repositories {
  maven { url = "https://maven.florens.be" }
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

Some example usages of the API can be found in the `testmod-[platform]` directories of this repository.

## To Do
- Swimming ability
  - Test with modded fluids
  - Test with elytra & Caelus API
  - Command
- Fluid walking ability
- Persistent status effects
- LivingEntity scaling
