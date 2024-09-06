import org.gradle.api.tasks.wrapper.Wrapper

plugins {
    id("idea")
}

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = project.properties["gradle.version"] as String
}

allprojects {
    repositories {
        mavenCentral()
    }
}
