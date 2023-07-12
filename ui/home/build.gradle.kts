apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.domainHome))
    "implementation"(Calendar.kizitonwose)

    "implementation"(Coil.coilCompose)
    "implementation"(Kotlin.coreKtx)
    "implementation"(Lifecycle.composeLifecycle)
    "implementation"(Compose.constraintLayout)
}
