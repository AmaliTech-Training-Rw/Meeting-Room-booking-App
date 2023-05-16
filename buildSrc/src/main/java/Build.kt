object Build {
    private const val androidBuildToolsVersion = "3.4.0"
    const val androidBuildTools = "com.android.tools.build:gradle:$androidBuildToolsVersion"

    const val kotlinGradlePlugin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Kotlin.version}"

    private const val appDistributionVersion = "3.0.1"
    const val appdistribution = "com.google.firebase:firebase-appdistribution-gradle:$appDistributionVersion"

    const val googleServicesVersion = "com.google.gms:google-services:4.3.15"

    private const val hiltAndroidGradlePluginVersion = "2.45"
    const val hiltAndroidGradlePlugin = "com.google.dagger:hilt-android-gradle-plugin:$hiltAndroidGradlePluginVersion"
}