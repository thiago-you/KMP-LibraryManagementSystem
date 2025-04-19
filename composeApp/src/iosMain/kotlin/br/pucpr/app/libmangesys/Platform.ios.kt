package br.pucpr.app.libmangesys

import br.pucpr.app.libmangesys.data.repositories.AppDatabase
import br.pucpr.app.libmangesys.database.getDatabaseBuilder
import org.koin.dsl.module
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun platformModule() = module {
    single<AppDatabase> {
        getDatabaseBuilder()
    }
}