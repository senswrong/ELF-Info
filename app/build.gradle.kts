plugins {
    id("buildsrc.convention.kotlin-jvm")
    application
}

application {
//    mainClass = "com.sens.api.app.AppKt"
    mainClass = "com.sens.api.app.CompareKt"
}

dependencies {
    implementation(project(":elf"))
}