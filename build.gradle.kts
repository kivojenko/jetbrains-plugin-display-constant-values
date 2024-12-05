plugins {
    id("java")
    id("org.jetbrains.intellij") version "1.17.4"
}

group = "com.kivojenko.plugin.display"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}


intellij {
    version.set("2024.3")
    type.set("PY")
    plugins.set(listOf("python")) // Ensures PyCharm's Python support is loaded
}

// Optional: Configure Java compatibility
java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


tasks {
    // Set the JVM compatibility versions
    withType<JavaCompile> {
        sourceCompatibility = "17"
        targetCompatibility = "17"
    }
}

