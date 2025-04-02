package br.pucpr.app.libmangesys

import android.app.Application
import org.koin.core.component.KoinComponent

class MainApplication :
    Application(),
    KoinComponent {

    override fun onCreate() {
        super.onCreate()
        KoinInitializer(applicationContext).init()
    }
}
