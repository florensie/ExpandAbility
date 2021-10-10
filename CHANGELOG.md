# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [4.0.2] - 2021-10-10
## Fixed
- New release without previously broken fix

## [4.0.1] - 2021-10-08 [YANKED]
### Fixed
- Crash on launch (fabric)
- 1.17 compatibility

## [4.0.0] - 2021-10-08 [YANKED]
### Changed
- Use Fabric API's TriState for the Fabric swimming event

### Fixed
- Depend on Minecraft 1.17+ instead of 1.17.1+

## [3.0.2] - 2021-10-04 [YANKED]
### Fixed
- Pulled in fix from 1.16 version (2.0.2)

## [2.0.2] - 2021-10-04 [YANKED]
### Fixed
- Adjustment to allow for better Mixin compatibility with other mods

## [3.0.1] - 2021-09-28
### Fixed
- Fixed dev dependencies defined for production build

## [3.0.0] - 2021-09-27
### Changed
- Update for 1.17.1

## [2.0.1] - 2021-04-15
### Fixed
- Icon crashing Forge's mod menu (lol)

## [2.0.0] - 2021-04-07
### Added
- LivingFluidCollision event which allows "fluid walking"
  - Massive thanks to Logantastic for providing his collision code!

## [1.1.0] - 2021-04-06
### Added
- Icon

### Fixed
- Bubble columns affect motion when water physics are disabled
- An incorrect translation key when dying from fall damage in water
- Block speed is not applied when in water and water physics are disabled
- Forwards collision with a block makes the player climb up with swimming enabled outside water 
- Cannot land critical hits when in water with water physics disabled
- Hunger is not applied correctly when swimming is enabled above water or disabled underwater
- Cannot start fall flying underwater with water physics disabled

## [1.0.0] - 2021-04-03
Initial release

### Added
- Player swim event

[Unreleased]: https://github.com/florensie/artifacts-fabric/compare/v4.0.2..HEAD
[4.0.2]: https://github.com/florensie/artifacts-fabric/compare/v4.0.1..v4.0.2
[4.0.1]: https://github.com/florensie/artifacts-fabric/compare/v4.0.0..v4.0.1
[4.0.0]: https://github.com/florensie/artifacts-fabric/compare/v3.0.2..v4.0.0
[3.0.2]: https://github.com/florensie/artifacts-fabric/compare/v3.0.1..v3.0.2
[2.0.2]: https://github.com/florensie/artifacts-fabric/compare/v2.0.1..v2.0.2
[3.0.1]: https://github.com/florensie/artifacts-fabric/compare/v3.0.0..v3.0.1
[3.0.0]: https://github.com/florensie/artifacts-fabric/compare/v2.0.1..v3.0.0
[2.0.1]: https://github.com/florensie/artifacts-fabric/compare/v2.0.0..v2.0.1
[2.0.0]: https://github.com/florensie/artifacts-fabric/compare/v1.1.0..v2.0.0
[1.1.0]: https://github.com/florensie/artifacts-fabric/compare/v1.0.0..v1.1.0
[1.0.0]: https://github.com/florensie/ExpandAbility/releases/tag/v1.0.0
