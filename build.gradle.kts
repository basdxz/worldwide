plugins {
    id("fpgradle-minecraft") version ("0.7.0")
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
    exclusive(mavenpattern(), "com.falsepattern")

    exclusive(maven("sk89q", "https://maven.enginehub.org/repo/"), "com.sk89q", "com.sk89q.lib")
}

dependencies {
    implementation("org.joml:joml:1.10.7")

    apiSplit("com.falsepattern:falsepatternlib-mc1.7.10:1.3.1")
    apiSplit("com.falsepattern:worldedit-mc1.7.10:6.3.0")
}
