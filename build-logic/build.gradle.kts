plugins {
    `kotlin-dsl`
    kotlin("jvm") version "1.9.22"
}

    repositories {
        mavenCentral()
        maven(uri("https://repo.runelite.net"))
        maven(uri("https://mvnrepository.com/artifact/net.runelite/fernflower"))
        gradlePluginPortal()
    }
gradlePlugin {
    plugins {
        register("fernflower"){
            id = "fernflower"
            implementationClass = "FernflowerPlugin"
        }
        register("bootstrap"){
            id = "bootstrap"
            implementationClass = "BootstrapPlugin"
        }

    }
}


dependencies{
    implementation(libs.runelite.fernflower)
    implementation(libs.json)
    implementation(libs.kotlin.gradle.plugin)

}