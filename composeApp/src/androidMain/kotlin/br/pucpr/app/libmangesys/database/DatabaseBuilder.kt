package br.pucpr.app.libmangesys.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import br.pucpr.app.libmangesys.data.repositories.AppDatabase

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("lib_manage_sys.db")

    return Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
}