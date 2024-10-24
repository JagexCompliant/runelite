/*
 * Copyright (c) 2019-2020 Owain van Brakel <https://github.com/Owain94>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */



plugins {
    application
    kotlin("jvm")
    `kotlin-dsl`

}


allprojects {
    group = "com.openosrs"
    version = "0.0.01"

    apply(plugin = "java")
    apply(plugin = "org.jetbrains.kotlin.jvm")

}

subprojects {
    repositories {
        if (System.getenv("JITPACK") != null) {
            mavenLocal()
        }

        exclusiveContent {
            forRepository {
                maven {
                    url = uri("https://jitpack.io")
                }
            }
            filter {
                includeGroup("com.github.petitparser.java-petitparser")
                includeModule("com.github.petitparser", "java-petitparser")
            }
        }

        exclusiveContent {
            forRepository {
                maven {
                    url = uri("https://repo.runelite.net")
                }
            }
            filter {
                includeGroup("net.runelite.rs")

                includeModule("net.runelite", "orange-extensions")
            }
        }

        exclusiveContent {
            forRepository {
                maven {
                    url = uri("https://raw.githubusercontent.com/open-osrs/hosting/master")
                }
            }
            filter {
                includeModule("net.runelite", "fernflower")
            }
        }

        mavenCentral()
    }

    apply(plugin = "java-library")

    project.extra["rootPath"] = rootDir.toString().replace("\\", "/")



    tasks {
        withType<JavaCompile>().configureEach {
            options.encoding = "UTF-8"
            sourceCompatibility = JavaVersion.VERSION_21.toString()
            targetCompatibility = JavaVersion.VERSION_21.toString()
        }


        withType<Checkstyle>().configureEach {
            group = "verification"

            exclude("**/ScriptVarType.java")
            exclude("**/LayoutSolver.java")
            exclude("**/RoomType.java")
        }

        withType<Jar>().configureEach {
            doLast {
                System.getProperty("signKeyStore")?.let { keyStore ->
                    ant.withGroovyBuilder {
                        "signjar"(
                            "keystore" to keyStore,
                            "storepass" to System.getProperty("signStorePass"),
                            "alias" to System.getProperty("signAlias"),
                            "jar" to outputs.files.singleFile,
                            "signedjar" to outputs.files.singleFile
                        )
                    }
                }
            }
        }
    }

    configurations.compileOnly.get().extendsFrom(configurations["annotationProcessor"])
}

application {
    mainClass.set("net.runelite.client.RuneLite")
}

tasks {
    named<JavaExec>("run") {
        group = "openosrs"
        classpath = project(":runelite-client").sourceSets.main.get().runtimeClasspath
        enableAssertions = true
    }
}