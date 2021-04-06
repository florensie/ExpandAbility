# Changelog
All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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

[2.0.0]: https://github.com/florensie/artifacts-fabric/compare/v1.1.0..v2.0.0
[1.1.0]: https://github.com/florensie/artifacts-fabric/compare/v1.0.0..v1.1.0
[1.0.0]: https://github.com/florensie/ExpandAbility/releases/tag/v1.0.0
