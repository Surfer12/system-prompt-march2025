rootProject.name = "system-prompt-march2025"

// Enable build cache
buildCache {
    local {
        isEnabled = true
    }
}

// Configure source sets and directories
sourceControl {
    gitRepository(uri("https://github.com/ryanoatespro/system-prompt-march2025.git")) {
        producesModule("com.ryanoatespro:system-prompt-march2025")
    }
}

// Plugin management
pluginManagement {
    repositories {
        gradlePluginPortal()
        mavenCentral()
        google()
    }
}

// Dependency resolution strategy
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_PROJECT)
    repositories {
        mavenCentral()
        maven {
            url = uri("https://repo.spring.io/milestone")
        }
        maven {
            url = uri("https://repo.spring.io/snapshot")
        }
    }
} 