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
        minecraftVersion = "1.21.11"

        modId = "expandability"
        name = "ExpandAbility"
        description = "Library mod that provides increased control over vanilla abilities"
        license = "MIT"

        author("Florens")
    }

    neoforge {
        loaderVersion = "21.11.38-beta"

        runs {
            client()
            server()
        }
    }

    fabric {
        loaderVersion = "0.18.4"

        includedClient()

        runs {
            client()
            server()
        }
    }
}
