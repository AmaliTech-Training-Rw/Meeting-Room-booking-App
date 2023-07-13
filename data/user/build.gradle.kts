apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    "implementation"(project(Modules.core))
    "implementation"(project(Modules.domainUser))

    "implementation"(Room.roomRuntime)
}
