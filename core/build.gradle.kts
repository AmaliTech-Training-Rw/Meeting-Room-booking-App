apply {
    from("$rootDir/base-module.gradle")
}

dependencies {
    // dependencies you may need in core
    "implementation"(Kotlin.coreKtx)
    "implementation"(Retrofit.okHttp)
    "implementation"(Retrofit.retrofit)
    "implementation"(Retrofit.okHttpLoggingInterceptor)
    "implementation"(Retrofit.moshiConverter)
}