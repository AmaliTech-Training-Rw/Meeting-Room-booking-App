apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.domainOnboarding))

    "implementation"(Kotlin.coreKtx)
    "implementation"(Lifecycle.composeLifecycle)
    "implementation"(Compose.constraintLayout)
}