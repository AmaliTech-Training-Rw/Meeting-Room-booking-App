apply {
    from("$rootDir/compose-module.gradle")
}
dependencies {
    "implementation"(project(Modules.coreUI))
    "implementation"(Kotlin.coreKtx)
    "implementation"(Lifecycle.composeLifecycle)

}