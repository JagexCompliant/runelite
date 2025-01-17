import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.*

class BootstrapPlugin : Plugin<Project> {
    override fun apply(project: Project): Unit = with(project) {
        val bootstrapDependencies by configurations.creating {
            isCanBeConsumed = false
            isCanBeResolved = true
            isTransitive = false
        }

        dependencies {
            bootstrapDependencies(project(":runelite-api"))
            bootstrapDependencies(project(":runescape-api"))
            bootstrapDependencies(project(":http-api"))
            bootstrapDependencies(project(":runelite-client"))
            bootstrapDependencies(project(":runelite-jshell"))
        }

        tasks.register<BootstrapTask>("bootstrapStaging", "staging")
        tasks.register<BootstrapTask>("bootstrapNightly", "nightly")
        tasks.register<BootstrapTask>("bootstrapStable", "stable")

        tasks.withType<BootstrapTask> {
            this.group = "openosrs"
            this.clientJar.fileProvider(provider { tasks["jar"].outputs.files.singleFile })

            dependsOn(bootstrapDependencies)
            dependsOn("publish")
            dependsOn(project(":runelite-api").tasks["publish"])
            dependsOn(project(":runescape-api").tasks["publish"])
            dependsOn(project(":http-api").tasks["publish"])
            dependsOn(project(":runelite-jshell").tasks["publish"])

            doLast {
                copy {
                    from(bootstrapDependencies)
                    into("${project.layout.buildDirectory}/bootstrap/${type}/")
                }
                copy {
                    from(
                            "${project.layout.buildDirectory}/repo/.",
                            "${parent?.projectDir}/runelite-api/build/repo/.",
                            "${parent?.projectDir}/http-api/build/repo/.",
                            "${parent?.projectDir}/runescape-api/build/repo/.",
                            "${parent?.projectDir}/runelite-jshell/build/repo/."
                    )
                    into("${project.layout.buildDirectory}/bootstrap/repo/${type}")
                }
            }
        }
    }
}
