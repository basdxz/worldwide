plugins {
    id("fpgradle-minecraft") version("0.5.0")
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
    mavenLocal()

    mavenpattern {
        content {
            includeGroup("com.falsepattern")
        }
    }

    exclusive(maven("sk89q", "https://maven.enginehub.org/repo/"), "com.sk89q", "com.sk89q.lib")
}

dependencies {
//    implementation("com.falsepattern:worldedit-mc1.7.10:6.2.0")
    implementation("com.falsepattern:worldedit-mc1.7.10:6.2.0-8-g4236472a:dev")
    implementation("com.falsepattern:falsepatternlib-mc1.7.10:1.3.0-1-g3b83315:dev")
}
