apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(Chart.mpAndroidChart)

    "implementation"(Coil.coilCompose)
    "implementation"(Kotlin.coreKtx)
    "implementation"(Lifecycle.composeLifecycle)
    "implementation"(Compose.constraintLayout)
}