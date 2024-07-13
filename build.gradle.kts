plugins {
    id("fpgradle-minecraft") version("0.4.2")
}

group = "com.mumfrey"

minecraft_fp {
    mod {
        modid   = "worldeditcui"
        name    = "WorldEditCUI"
        rootPkg = "$group.worldeditcui"
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
    implementation("com.falsepattern:worldedit-mc1.7.10:6.2.0")
}
