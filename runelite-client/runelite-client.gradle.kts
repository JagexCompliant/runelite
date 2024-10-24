/*
 * Copyright (c) 2019 Owain van Brakel <https://github.com/Owain94>
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

import java.text.SimpleDateFormat
import java.util.Date

plugins {

    kotlin("jvm")
    java
}

repositories {
    mavenCentral()
    maven {
        url = uri("https://jitpack.io")
        url = uri("https://repo.runelite.net")
    }
    maven(uri("https://mvnrepository.com/artifact/net.runelite/fernflower"))

}



description = "RuneLite Client"

dependencies {
    with(libs) {
    annotationProcessor(lombok)
    annotationProcessor(pf4j)

    api(projects.runeliteApi)

    compileOnly(javax.annotation)
    compileOnly(lombok)
    compileOnly(orange.extensions)

    implementation(projects.httpApi)
    implementation(projects.runeliteJshell)
    implementation(logback)
    implementation(gson)
    implementation(guava) {
        exclude(group = "com.google.code.findbugs", module = "jsr305")
        exclude(group = "com.google.errorprone", module = "error_prone_annotations")
        exclude(group = "com.google.j2objc", module = "j2objc-annotations")
        exclude(group = "org.codehaus.mojo", module = "animal-sniffer-annotations")
    }
        implementation(guice)
        implementation(protobuf)
        implementation(rxrelay)
        implementation(okhttp)
        implementation(rxjava)
        implementation(jgroups)
        implementation(jna.platform)
        implementation(jna)
        implementation(substance)
        implementation(jopt.simple)
        implementation(desktopsupport)
        implementation(apache.commons.text)
        implementation(apache.csv)
        implementation(commons.io)
        implementation(annotations)
        implementation(java.semver)
        implementation(slf4j.api)
        implementation(pf4j) {
            exclude(group = "org.slf4j")
        }
        implementation(pf4j.update)
        // implementation(group = "com.google.archivepatcher", name = "archive-patch-applier", version= "1.0.4")
        implementation(gluegen)
        implementation(jogl.rl)
        implementation(jogl.desktop)
        implementation(jocl)

        runtimeOnly(projects.runescapeApi)
        runtimeOnly(trident)
        runtimeOnly(gluegen, "natives-linux-amd64")
        runtimeOnly(gluegen, "natives-windows-amd64")
        runtimeOnly(gluegen, "natives-windows-i586")
        runtimeOnly(gluegen, "natives-macosx-universal")
        runtimeOnly(gluegen, "natives-linux-amd64")
        runtimeOnly(jogl.rl, "natives-windows-amd64")
        runtimeOnly(jogl.rl,"natives-windows-i586")
        runtimeOnly(jogl.rl, "natives-macosx-universal")
        runtimeOnly(jocl, "macos-x64")
        runtimeOnly(jocl, "macos-arm64")


    }
}

fun formatDate(date: Date?) = with(date ?: Date()) {
    SimpleDateFormat("MM-dd-yyyy").format(this)
}

fun pluginPath(): String {
    if (project.hasProperty("pluginPath")) {
        return project.property("pluginPath").toString()
    }
    return ""
}

tasks {




    jar {
        manifest {
            attributes(mutableMapOf("Main-Class" to "net.runelite.client.RuneLite"))
        }
    }


/*    assembleScripts {
        val inp = "${projectDir}/src/main/scripts"
        val out = "${buildDir}/scripts/runelite"

        inputs.dir(inp)
        outputs.dir(out)

        input.set(file(inp))
        output.set(file(out))
    }*/

   // processResources {
    //    dependsOn("assembleScripts")
      //  dependsOn(":injected-client:inject")

      //  from("${layout.buildDirectory}/scripts")

       // from("${project(":injected-client").buildDir}/libs")
       // from("${project(":injected-client").buildDir}/resources/main")
  //  }



    register<JavaExec>("RuneLite.main()") {
        group = "openosrs"

        classpath = project.sourceSets.main.get().runtimeClasspath
        enableAssertions = true
        mainClass.set("net.runelite.client.RuneLite")
    }
}

fun DependencyHandler.runtimeOnly(dep: Provider<MinimalExternalModuleDependency>, classifier: String) = runtimeOnly(dep.get().group, dep.get().name, dep.get().version, classifier =  classifier)
