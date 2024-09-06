rootProject.name = "spring-react-demo"

include(
    "api"
)


pluginManagement {

    plugins {
        id("org.springframework.boot") version settings.extra["plugin.spring-boot.version"]!!.toString() apply false
        id("io.spring.dependency-management") version settings.extra["plugin.spring-dependency-management.version"]!!.toString() apply false
        id("com.github.hauner.jarTest") version settings.extra["plugin.jarTest.version"]!!.toString() apply false
        id("io.swagger.core.v3.swagger-gradle-plugin") version settings.extra["plugin.swagger.version"]!!.toString() apply false
    }

    repositories {
        mavenCentral()
    }

}
