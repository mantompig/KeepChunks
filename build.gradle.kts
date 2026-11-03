plugins {
    java
}

group = "com.geitenijs"
version = "1.7.4"

repositories {
    mavenCentral()

    maven {
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }

    maven {
        url = uri("https://maven.enginehub.org/repo/")
    }
}

dependencies {
    // Paper 26.3 API
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")

    // WorldEdit integration
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.3.+")

    // WorldGuard integration
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.+")

    // JUnit (for future tests)
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(25))
    }
}

// Keep the existing source directory structure
sourceSets {
    main {
        java.srcDirs("src")
        resources.srcDirs("src")
        resources.include("plugin.yml")
    }
}

// Don't accidentally compile plugin.yml as Java
tasks.processResources {
    filesMatching("plugin.yml") {
        expand("version" to project.version)
    }
}

tasks.test {
    useJUnitPlatform()
}

tasks.jar {
    archiveBaseName.set("KeepChunks")
    archiveVersion.set(project.version.toString())
}
