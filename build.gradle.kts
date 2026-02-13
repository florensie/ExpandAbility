plugins {
    id("earth.terrarium.cloche") version "0.18.1"
}

repositories {
    cloche.librariesMinecraft()
    mavenCentral()

    cloche {
        main()
        mavenFabric()
        mavenNeoforgedMeta()
        mavenNeoforged()
    }
}

cloche {
    metadata {
        modId = "expandability"
        name = "ExpandAbility"
        description = "Library mod that provides increased control over vanilla abilities"
        license = "MIT"
        icon = "icon.png"

        sources = "https://github.com/florensie/expandability"
        author("Florens")
    }

    common {
        mixins.from(file("src/common/main/expandability.mixins.json5"))

//        test()

        mappings {
            official()
        }
    }

    neoforge {
        minecraftVersion = "1.21.11"
        loaderVersion = "21.11.38-beta"
        mixins.from(file("src/neoforge/main/expandability.neoforge.mixins.json5"))

//        test()

        runs {
            server()
            client()

//            test()
//            clientTest()
        }
    }

    fabric {
        minecraftVersion = "1.21.11"
        loaderVersion = "0.18.4"
        mixins.from(file("src/fabric/main/expandability.fabric.mixins.json5"))

        includedClient()
        test()

        dependencies {
            fabricApi("0.141.3")
        }

        runs {
            server()
            client()

            test()
            clientTest()
        }
    }
}
