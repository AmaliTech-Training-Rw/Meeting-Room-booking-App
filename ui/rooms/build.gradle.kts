apply {
    from("$rootDir/compose-module.gradle")
}
dependencies {
    "implementation"(project(Modules.coreUI))
    "implementation"(project(Modules.domainRooms))
    "implementation"(project(Modules.core))
    "implementation"(DatePicker.vanpra)
    "implementation"(Coil.coilCompose)
    "implementation"(Kotlin.coreKtx)

}