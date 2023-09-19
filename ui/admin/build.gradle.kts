apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.domainDashboard))
    "implementation"(Chart.mpAndroidChart)

    "implementation"(Kotlin.coreKtx)
    "implementation"(Lifecycle.composeLifecycle)
}
