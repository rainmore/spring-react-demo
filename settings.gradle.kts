rootProject.name = "spring-react-demo"

include(
    "entity",
    "api"
)


pluginManagement {

    plugins {
        id("org.springframework.boot") version settings.extra["plugin.spring-boot.version"]!!.toString() apply false
        id("io.spring.dependency-management") version settings.extra["plugin.spring-dependency-management.version"]!!.toString() apply false
        id("org.springdoc.openapi-gradle-plugin") version settings.extra["plugin.springdoc-openapi.version"]!!.toString() apply false
        id("com.github.hauner.jarTest") version settings.extra["plugin.jarTest.version"]!!.toString() apply false
        id("io.swagger.core.v3.swagger-gradle-plugin") version settings.extra["plugin.swagger.version"]!!.toString() apply false
        // required by `org.springdoc.openapi-gradle-plugin`
        id("com.github.psxpaul.execfork") version settings.extra["plugin.springdoc-psxpaul-execfork.version"]!!.toString() apply false
    }

    repositories {
        maven { url = uri("https://plugins.gradle.org/m2/") }
        mavenCentral()
    }

}
