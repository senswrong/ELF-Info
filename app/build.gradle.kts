plugins {
    id("buildsrc.convention.kotlin-jvm")
    application
}

application {
    mainClass = "com.sens.api.app.AppKt"
}

dependencies {
    implementation(project(":elf"))
}