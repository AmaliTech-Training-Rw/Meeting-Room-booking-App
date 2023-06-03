apply {
    from("$rootDir/compose-module.gradle")
}
dependencies {
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.uiSwipe))
}