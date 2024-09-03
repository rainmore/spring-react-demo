import java.nio.file.Path
import org.gradle.api.tasks.wrapper.Wrapper

plugins {
    application
    idea
}

group = "au.com.rainmore.centus"
version = "24.09"
description = "Centus - Spring React Demo"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
        vendor.set(JvmVendorSpec.ADOPTIUM)
        implementation.set(JvmImplementation.VENDOR_SPECIFIC)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
}

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = project.properties["gradleVersion"] as String
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("-Xlint:unchecked")
}

application {
    mainClass.set(listOf(project.group.toString(), "Application").joinToString("."))
}

tasks.withType<Jar> {
    manifest {
        attributes["Main-Class"] = application.mainClass.get()
    }
}


sourceSets {
    create("testInt") {
        java {
            compileClasspath += sourceSets.main.get().output + sourceSets.test.get().output
            runtimeClasspath += sourceSets.main.get().output + sourceSets.test.get().output
        }
    }
}

configurations {
    all {
        exclude("org.apache.logging.log4j:*")
    }

    val testIntCompileOnly by getting {
        extendsFrom(
            configurations.compileOnly.get(),
            configurations.testCompileOnly.get()
        )
    }

    val testIntImplementation by getting {
        extendsFrom(
            configurations.implementation.get(),
            configurations.testImplementation.get()
        )
    }

    val testIntRuntimeOnly by getting {
        extendsFrom(
            configurations.runtimeOnly.get(),
            configurations.testRuntimeOnly.get()
        )
    }
}

tasks.withType<Test> {
    useJUnitPlatform {
        testLogging {
            events("passed", "skipped", "failed", "standard_error")
        }
    }
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}
