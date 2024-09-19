import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
    id("java")
    id("application")
    id("org.springframework.boot")
    id("io.spring.dependency-management")
    id("com.github.psxpaul.execfork")
    id("org.springdoc.openapi-gradle-plugin")
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
    implementation(project(":api:entity"))

    // Spring
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    runtimeOnly("org.springframework.boot:spring-boot-properties-migrator")

    implementation("org.springframework.boot:spring-boot-autoconfigure")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-tomcat")
    implementation("org.springframework.boot:spring-boot-starter-logging")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")

    // JWT
    implementation("com.auth0:java-jwt:${project.properties["java-jwt.version"]}")

    // API
    implementation("io.swagger.core.v3:swagger-annotations:${project.properties["swagger-annotations.version"]}")
    implementation("cz.jirutka.rsql:rsql-parser:${project.properties["rsql-parser.version"]}")
    runtimeOnly("org.springdoc:springdoc-openapi-starter-webmvc-ui:${project.properties["springdoc-openapi.version"]}")

    // JPA & DB
    runtimeOnly("com.h2database:h2")
    implementation("com.zaxxer:HikariCP")
    implementation("org.hibernate.orm:hibernate-core")
    implementation("org.hibernate.validator:hibernate-validator")

    implementation("com.querydsl:querydsl-jpa") { artifact { classifier = "jakarta" } }
    implementation("com.querydsl:querydsl-sql-spring")

    // Logging
    implementation("org.slf4j:slf4j-api")
    implementation("org.slf4j:jcl-over-slf4j")
    implementation("org.slf4j:log4j-over-slf4j")
    implementation("ch.qos.logback:logback-classic")
    implementation("ch.qos.logback:logback-core")

    // Jackson Json
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-hibernate5-jakarta")

    // Utilities
    implementation("org.apache.commons:commons-lang3")
    implementation("jakarta.servlet:jakarta.servlet-api")

    // Testing
//    testImplementation(platform("org.junit:junit-bom:${project.properties["junit-bom.version"]}"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Test Utilities
    testImplementation("com.devskiller:jfairy:${project.properties["devskiller-jfairy.version"]}")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core") //
}


tasks.withType<BootRun> {
    args("--spring.profiles.active=dev")
    jvmArgs = listOf(
        "-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:5005"
    )
}
