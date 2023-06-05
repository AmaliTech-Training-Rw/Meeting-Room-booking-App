apply {
    from("$rootDir/compose-module.gradle")
}
dependencies {
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.uiSwipe))
    "implementation"(project(Modules.domainRoom))
    "implementation"(project(Modules.core))
    "implementation"(Coil.coilCompose)
    "implementation"(Kotlin.coreKtx)

}