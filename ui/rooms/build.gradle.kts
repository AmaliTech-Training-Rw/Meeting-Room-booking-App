apply {
    from("$rootDir/compose-module.gradle")
}
dependencies {
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.domainRooms))
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.domainOnboarding))
    "implementation"(Coil.coilCompose)
    "implementation"(Kotlin.coreKtx)
    "implementation"(Compose.constraintLayout)
    "implementation"(Permissions.accompanist)
}