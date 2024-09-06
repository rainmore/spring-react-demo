import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    application
    idea
    id("org.springframework.boot") version "3.3.3"
    id("io.spring.dependency-management") version "1.1.5"
}

group = "au.com.rainmore.centus"
version = "24.09"
description = "Centus - Spring React Demo"

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
        vendor.set(JvmVendorSpec.ADOPTIUM)
        implementation.set(JvmImplementation.VENDOR_SPECIFIC)
    }
}

allprojects {
    repositories {
        mavenCentral()
    }
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
    // Spring
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("org.springframework.boot:spring-boot-properties-migrator")

    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")

    // API
    implementation("io.swagger.core.v3:swagger-annotations:2.2.22")
    implementation("cz.jirutka.rsql:rsql-parser:2.1.0")


    // Logging
    implementation("org.slf4j:slf4j-api:2.0.16")
    implementation("org.slf4j:jcl-over-slf4j:2.0.16")
    implementation("org.slf4j:log4j-over-slf4j:2.0.16")
    implementation("ch.qos.logback:logback-classic:1.5.7")
    implementation("ch.qos.logback:logback-core:1.5.7")

    // Jackson Json
    implementation("com.fasterxml.jackson.core:jackson-core:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5-jakarta:2.17.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5-jakarta:2.17.2")

    // Utilities
    implementation("org.apache.commons:commons-lang3:3.17.0")

    // Testing
    testImplementation(platform("org.junit:junit-bom:5.11.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Test Utilities
    testImplementation("com.devskiller:jfairy:0.6.5")
    testImplementation("org.mockito:mockito-core:5.11.0")
    testImplementation("org.assertj:assertj-core:3.25.3")
}


tasks.withType<BootRun> {
    args("--spring.profiles.active=dev")
    jvmArgs = listOf(
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
    )
}
