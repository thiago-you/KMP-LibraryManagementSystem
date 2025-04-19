package br.pucpr.app.libmangesys.database

import androidx.room.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import br.pucpr.app.libmangesys.data.repositories.AppDatabase
import platform.Foundation.NSHomeDirectory

fun getDatabaseBuilder(): AppDatabase {
    val dbFilePath = NSHomeDirectory() + "/lib_manage_sys.db"

    return Room.databaseBuilder<AppDatabase>(
        name = dbFilePath,
    )
        .setDriver(BundledSQLiteDriver())
        .build()
}