# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [11.0.0] - 2024-06-07
### Changed
- Update for Minecraft 1.20.6

## [10.0.1] - 2024-04-29
### Fixed
- Fix for non-player entities no longer being pushed by fluids

## [9.0.4] - 2024-04-29
### Fixed
- Fix for non-player entities no longer being pushed by fluids

## [10.0.0] - 2024-04-27
### Changed
- Update for Minecraft 1.20.4

## [9.0.3] - 2024-04-20
### Fixed
- Crash on launch (forge v47)

## [9.0.2] - 2024-04-19
### Fixed
- With fluid physics disabled, fluid pushing would still occur

## [9.0.1] - 2024-04-12
### Fixed
- Rewrote fluid collisions, fixing multiple issues:
  - Fixed a server freeze due to the collision code getting into an infinite loop
  - Fixed sneaking at the edge of a fluid block not behaving as with regular blocks
  - Fixed no fall damage dealt for falling on a fluid block

## [9.0.0] - 2023-07-07
### Changed
- Update for Minecraft 1.20

## [8.0.0] - 2023-03-22
### Changed
- Update for Minecraft 1.19.4

## [7.0.0] - 2022-09-11
### Changed
- Support for Forge's new Fluid API introduced in 1.19
- Improved compatibility of mixins

### Fixed
- Fix fall damage falling in fluid

## [6.0.0] - 2022-03-06
### Changed
- Update for Minecraft 1.18.2
- Now using Slf4j
- Marked some methods as internal

### Fixed
- Mixin injections didn't have the proper requirements, meaning some failed injections could go unnoticed
- Typo in javadoc

## [5.0.0] - 2021-12-26
### Changed
- Update for Minecraft 1.18

## [4.0.2] - 2021-10-10
### Fixed
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

[Unreleased]: https://github.com/florensie/ExpandAbility/compare/v11.0.0..HEAD
[11.0.0]: https://github.com/florensie/ExpandAbility/compare/v10.0.1..v11.0.0
[10.0.1]: https://github.com/florensie/ExpandAbility/compare/v10.0.0..v10.0.1
[10.0.0]: https://github.com/florensie/ExpandAbility/compare/v9.0.4..v10.0.0
[9.0.4]: https://github.com/florensie/ExpandAbility/compare/v9.0.3..v9.0.4
[9.0.3]: https://github.com/florensie/ExpandAbility/compare/v9.0.2..v9.0.3
[9.0.2]: https://github.com/florensie/ExpandAbility/compare/v9.0.1..v9.0.2
[9.0.1]: https://github.com/florensie/ExpandAbility/compare/v9.0.0..v9.0.1
[9.0.0]: https://github.com/florensie/ExpandAbility/compare/v8.0.0..v9.0.0
[8.0.0]: https://github.com/florensie/ExpandAbility/compare/v7.0.0..v8.0.0
[7.0.0]: https://github.com/florensie/ExpandAbility/compare/v6.0.0..v7.0.0
[6.0.0]: https://github.com/florensie/ExpandAbility/compare/v5.0.0..v6.0.0
[5.0.0]: https://github.com/florensie/ExpandAbility/compare/v4.0.2..v5.0.0
[4.0.2]: https://github.com/florensie/ExpandAbility/compare/v4.0.1..v4.0.2
[4.0.1]: https://github.com/florensie/ExpandAbility/compare/v4.0.0..v4.0.1
[4.0.0]: https://github.com/florensie/ExpandAbility/compare/v3.0.2..v4.0.0
[3.0.2]: https://github.com/florensie/ExpandAbility/compare/v3.0.1..v3.0.2
[2.0.2]: https://github.com/florensie/ExpandAbility/compare/v2.0.1..v2.0.2
[3.0.1]: https://github.com/florensie/ExpandAbility/compare/v3.0.0..v3.0.1
[3.0.0]: https://github.com/florensie/ExpandAbility/compare/v2.0.1..v3.0.0
[2.0.1]: https://github.com/florensie/ExpandAbility/compare/v2.0.0..v2.0.1
[2.0.0]: https://github.com/florensie/ExpandAbility/compare/v1.1.0..v2.0.0
[1.1.0]: https://github.com/florensie/ExpandAbility/compare/v1.0.0..v1.1.0
[1.0.0]: https://github.com/florensie/ExpandAbility/releases/tag/v1.0.0
