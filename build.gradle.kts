plugins {
    java
}

group = "com.geitenijs"
version = "1.7.4"

repositories {
    mavenCentral()

    maven {
        name = "PaperMC"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        name = "EngineHub"
        url = uri("https://maven.enginehub.org/repo/")
    }
}

dependencies {
    // Paper 26.3 API
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")

    // WorldEdit integration
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.4.6")

    // WorldGuard integration
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.22-SNAPSHOT")

    // Guava is used directly by KeepChunks and exposed by Paper's API.
    // It is provided by the Minecraft server at runtime.
    compileOnly("com.google.guava:guava:33.6.0-jre")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}

// Avoid dependency conflicts between Paper, WorldEdit and WorldGuard.
configurations.configureEach {
    exclude(group = "com.google.code.gson", module = "gson")
    exclude(group = "org.apache.logging.log4j", module = "log4j-bom")

    resolutionStrategy {
        force("com.google.guava:guava:33.6.0-jre")
    }
}

// Java 25 for Paper 26.3
java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

// Keep the original KeepChunks source directory structure.
sourceSets {
    main {
        java {
            setSrcDirs(listOf("src"))
        }

        resources {
            setSrcDirs(listOf("src"))
            include("plugin.yml")
        }
    }
}

// Replace ${version} in plugin.yml with the Gradle project version.
tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

// Enable JUnit 5
tasks.test {
    useJUnitPlatform()
}

// Configure the output JAR.
tasks.jar {
    archiveBaseName.set("KeepChunks")
    archiveVersion.set(project.version.toString())
}
