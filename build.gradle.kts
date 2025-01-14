plugins {
    id("fpgradle-minecraft") version ("0.7.0")
}

group = "com.ventooth"

minecraft_fp {
    mod {
        modid = "worldwide"
        name = "Worldwide"
        rootPkg = "$group.worldwide"
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
    implementation("org.joml:joml:1.10.8")

    apiSplit("com.falsepattern:falsepatternlib-mc1.7.10:1.4.0")
    apiSplit("com.falsepattern:worldedit-mc1.7.10:6.3.0")
}
