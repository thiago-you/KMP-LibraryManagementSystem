package br.pucpr.app.libmangesys

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KoinInitializer {
    actual fun init() {
        initKoin {
            modules(appModule, viewModelModule)
        }
    }
}