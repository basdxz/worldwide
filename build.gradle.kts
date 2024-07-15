plugins {
    id("fpgradle-minecraft") version("0.6.0")
}

group = "com.ventooth"

minecraft_fp {
    mod {
        modid   = "worldeditcui"
        name    = "WorldEditCUI"
        rootPkg = "com.mumfrey.worldeditcui"
    }

    tokens {
        tokenClass = "Tags"
    }
}

repositories {
    //TODO: Remove mavenLocal()
    mavenLocal()

    mavenpattern {
        content {
            includeGroup("com.falsepattern")
        }
    }

    exclusive(maven("sk89q", "https://maven.enginehub.org/repo/"), "com.sk89q", "com.sk89q.lib")
}

dependencies {
    apiSplit("com.falsepattern:falsepatternlib-mc1.7.10:1.3.0-1-g3b83315:dev")

    //TODO: Switch to Non-local pre-release
    implementation("com.falsepattern:worldedit-mc1.7.10:6.2.0-9-g2be82955:dev")
}
