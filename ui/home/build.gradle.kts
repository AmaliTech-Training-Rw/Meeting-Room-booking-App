apply {
    from("$rootDir/compose-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.domainHome))
    "implementation"(Calendar.kizitonwose)
    "implementation"(DatePicker.vanpra)
    "implementation"(Coil.coilCompose)
}
