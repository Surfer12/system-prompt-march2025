import java.time.LocalDateTime

plugins {
    java
    application
    id("org.springframework.boot") version "3.1.5" apply false
    id("io.spring.dependency-management") version "1.1.3" apply false
}

group = "com.ryanoatespro"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    // YAML parsing
    implementation("org.yaml:snakeyaml:2.0")

    // Logging
    implementation("org.slf4j:slf4j-api:2.0.7")
    implementation("ch.qos.logback:logback-classic:1.4.7")

    // Testing
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.9.3")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.9.3")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.test {
    useJUnitPlatform()
}

// Custom tasks for the project
tasks.register("systemInfo") {
    group = "system"
    description = "Displays information about the system architecture"
    doLast {
        println("System Prompt March 2025 Project")
        println("Java Version: ${System.getProperty("java.version")}")
        println("Operating System: ${System.getProperty("os.name")} ${System.getProperty("os.version")}")
    }
}

tasks.register("generateStoryTellerReport") {
    group = "reporting"
    description = "Generates a report about the StoryTeller system"
    doLast {
        val reportDir = File("${buildDir}/reports/storyteller")
        reportDir.mkdirs()
        
        val reportFile = File(reportDir, "storyteller_report.txt")
        reportFile.writeText("""
        StoryTeller System Report
        ========================
        
        Components:
        - Librarian: Central system orchestration
        - StoryTeller: Interactive narrative interface
        
        Key Features:
        1. Dynamic story generation
        2. Technology gate exploration
        3. Creative principles visualization
        
        Generated on: ${LocalDateTime.now()}
        """.trimIndent())
        
        println("StoryTeller report generated at ${reportFile.absolutePath}")
    }
}

tasks.register("interactiveNarrative") {
    group = "application"
    description = "Runs the interactive StoryTeller narrative"
    doLast {
        javaexec {
            mainClass.set("com.uplift.system.library.StoryTeller")
            classpath = sourceSets.main.get().runtimeClasspath
        }
    }
}

tasks.register("exploreGateTechnologies") {
    group = "exploration"
    description = "Explores and lists technologies in the Gate system"
    doLast {
        println("Exploring Gate Technologies:")
        println("1. Mojo Connector")
        println("2. Swift Connector")
        println("3. C++ Connector")
        println("4. Java Connector")
    }
}

// Task to create a distribution package
tasks.register<Zip>("createSystemDistribution") {
    group = "distribution"
    description = "Creates a distribution package of the system"
    
    archiveBaseName.set("system-prompt-march2025")
    archiveVersion.set("1.0")
    archiveExtension.set("zip")
    
    from(projectDir) {
        include("src/**")
        include("build.gradle.kts")
        include("pom.xml")
        include("README.md")
        exclude("build")
        exclude(".gradle")
    }
    
    destinationDirectory.set(file("${buildDir}/distributions"))
}

// Default task
defaultTasks("systemInfo")
// Remove duplicate dependency declaration
//dependencies {
//    implementation 'org.yaml:snakeyaml:1.30'
//} 
