description = "Cache"

dependencies {
    with(libs) {
        annotationProcessor(lombok)
        api(projects.httpApi)
        compileOnly(lombok)
        implementation(gson)
        implementation(guava)
        implementation(commons.cli)
        implementation(netty.buffer)
        implementation(okhttp)
        implementation(antlr)
        implementation(apache.compress)
        implementation(slf4j.api)
    }
}

