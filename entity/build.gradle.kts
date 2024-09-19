import io.spring.gradle.dependencymanagement.dsl.DependencyManagementExtension

plugins {
    id("java-library")
    id("org.springframework.boot") apply false
    id("io.spring.dependency-management")
}

the<DependencyManagementExtension>().apply {
    imports {
        mavenBom(org.springframework.boot.gradle.plugin.SpringBootPlugin.BOM_COORDINATES)
    }
}

group = "au.com.rainmore.centus"
description = "Centus - Entity"

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

configurations {
    all {
        exclude("org.apache.logging.log4j:*")
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
    // JPA & DB
    implementation("org.hibernate.validator:hibernate-validator")

    implementation("com.querydsl:querydsl-apt")
    implementation("com.querydsl:querydsl-sql")
    implementation("com.querydsl:querydsl-jpa") { artifact { classifier = "jakarta" } }
    implementation("com.querydsl:querydsl-sql-spring")

    // Generated Entities
    annotationProcessor("com.querydsl:querydsl-apt") { artifact { classifier = "jakarta" } }
    annotationProcessor("jakarta.persistence:jakarta.persistence-api")
    annotationProcessor("jakarta.annotation:jakarta.annotation-api")

    implementation("jakarta.validation:jakarta.validation-api")
    implementation("jakarta.persistence:jakarta.persistence-api")
    implementation("org.jetbrains:annotations:${project.properties["jetbrains-annotations.version"]}")

    // Logging
    implementation("org.slf4j:slf4j-api")

    // Utilities
    implementation("org.apache.commons:commons-lang3")

    // Testing
//    testImplementation(platform("org.junit:junit-bom:${project.properties["junit-bom.version"]}"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    // Test Utilities
    testImplementation("com.devskiller:jfairy:${project.properties["devskiller-jfairy.version"]}")
    testImplementation("org.mockito:mockito-core")
    testImplementation("org.mockito:mockito-junit-jupiter")
    testImplementation("org.assertj:assertj-core") //
}
