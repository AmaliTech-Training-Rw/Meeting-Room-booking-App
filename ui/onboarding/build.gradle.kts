apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.domainOnboarding))

    "implementation"(Coil.coilCompose)
    "implementation"(Kotlin.coreKtx)
}