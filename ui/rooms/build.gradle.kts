apply {
    from("$rootDir/compose-module.gradle")
}
dependencies {
    "implementation"(project(Modules.uiSwipe))
    "implementation"(project(Modules.coreUI))
}