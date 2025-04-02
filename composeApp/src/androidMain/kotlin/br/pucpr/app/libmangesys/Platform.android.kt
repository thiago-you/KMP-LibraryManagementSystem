package br.pucpr.app.libmangesys

import android.os.Build
import androidx.room.RoomDatabase
import br.pucpr.app.libmangesys.data.repositories.AppDatabase
import br.pucpr.app.libmangesys.database.getDatabaseBuilder
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun platformModule() = module {
    single<RoomDatabase.Builder<AppDatabase>> {
        getDatabaseBuilder(get())
    }
}

val appModule = module {}