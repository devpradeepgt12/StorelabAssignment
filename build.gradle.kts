plugins {
    // this is necessary to avoid the plugins to be loaded multiple times
    // in each subproject's classloader
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.composeHotReload) apply false
    alias(libs.plugins.composeMultiplatform) apply false
    alias(libs.plugins.composeCompiler) apply false
    alias(libs.plugins.kotlinMultiplatform) apply false
}

import org.gradle.api.tasks.testing.Test

tasks.register("runAllTests") {
    group = "verification"
    description = "Runs all unit tests for all modules."
    dependsOn(subprojects.flatMap { it.tasks.withType<Test>() })

    doLast {
        println("All tests have been executed. Reports are in each module's 'build/reports' directory.")
    }
}
