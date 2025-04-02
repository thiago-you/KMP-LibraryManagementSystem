package br.pucpr.app.libmangesys

import android.content.Context
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class KoinInitializer(
    private val context: Context
) {
    actual fun init() {
        initKoin {
            androidLogger()
            androidContext(context)
            modules(appModule, viewModelModule)
        }
    }
}