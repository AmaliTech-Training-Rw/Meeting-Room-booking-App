apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.domainOnboarding))
    "implementation"(project(Modules.core))
    "testImplementation"(project(mapOf("path" to Modules.core)))

    "implementation"(Coil.coilCompose)
    "implementation"(Kotlin.coreKtx)
    "implementation"(Lifecycle.composeLifecycle)
}