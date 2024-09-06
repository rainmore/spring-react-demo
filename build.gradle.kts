import org.gradle.api.tasks.wrapper.Wrapper

plugins {
    idea
}

tasks.named<Wrapper>("wrapper").configure {
    distributionType = Wrapper.DistributionType.ALL
    gradleVersion = project.properties["gradleVersion"] as String
}
