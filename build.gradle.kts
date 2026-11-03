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
    // Paper 26.3
    compileOnly("io.papermc.paper:paper-api:26.3.build.+")

    // WorldEdit
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.4.6")

    // WorldGuard
    compileOnly("com.sk89q.worldguard:worldguard-bukkit:7.0.22-SNAPSHOT")

    // Tests
    testImplementation("org.junit.jupiter:junit-jupiter:5.11.4")
}

configurations.configureEach {
    exclude(group = "com.google.guava", module = "guava")
    exclude(group = "com.google.code.gson", module = "gson")
    exclude(group = "org.apache.logging.log4j", module = "log4j-bom")
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
