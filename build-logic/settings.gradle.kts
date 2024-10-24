@file:Suppress("UnstableApiUsage")
plugins{
    id("org.gradle.toolchains.foojay-resolver-convention") version("0.5.0")

}
rootProject.name = "build-logic"

dependencyResolutionManagement {

    versionCatalogs {
        create("libs") {
            from(files("../gradle/libs.versions.toml"))
        }
    }

}