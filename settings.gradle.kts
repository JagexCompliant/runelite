@file:Suppress("UnstableApiUsage")

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
dependencyResolutionManagement{
    repositories{
        gradlePluginPortal()
        mavenCentral()
    }
}

rootProject.name = "OpenOSRS"
plugins {
    kotlin("jvm") version "1.9.22" apply false
    id("de.fayard.refreshVersions") version "0.60.5"
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")
includeBuild("build-logic")
include(":http-api")
include(":cache")
include(":runelite-api")
include(":injection-annotations")
include(":runescape-api")
include(":runescape-client")
include(":runelite-mixins")
include(":wiki-scraper")
include(":deobfuscator")
include(":runelite-jshell")
include(":runelite-client")
/*
include(":injected-client")
*/

for (project in rootProject.children) {
    project.apply {
        projectDir = file(name)
        buildFileName = "$name.gradle.kts"

        require(projectDir.isDirectory) { "Project '${project.path} must have a $projectDir directory" }
        require(buildFile.isFile) { "Project '${project.path} must have a $buildFile build script" }
    }
}
