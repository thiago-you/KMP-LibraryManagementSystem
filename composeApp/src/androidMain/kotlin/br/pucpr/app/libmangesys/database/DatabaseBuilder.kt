package br.pucpr.app.libmangesys.database

import android.content.Context
import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import br.pucpr.app.libmangesys.data.repositories.AppDatabase

fun getDatabaseBuilder(ctx: Context): AppDatabase {
    val dbFile = ctx.getDatabasePath("lib_manage_sys.db")

    return Room.databaseBuilder<AppDatabase>(
        context = ctx.applicationContext,
        name = dbFile.absolutePath
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}