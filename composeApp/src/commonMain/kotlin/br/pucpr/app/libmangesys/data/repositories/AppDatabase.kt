package br.pucpr.app.libmangesys.data.repositories

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import br.pucpr.app.libmangesys.data.models.Book
import br.pucpr.app.libmangesys.data.models.Borrow
import br.pucpr.app.libmangesys.data.models.User
import br.pucpr.app.libmangesys.data.repositories.book.BookDao
import br.pucpr.app.libmangesys.data.repositories.borrow.BorrowDao
import br.pucpr.app.libmangesys.data.repositories.user.UserDao

@Database(version = 2, exportSchema = false, entities = [Book::class, User::class, Borrow::class])
@ConstructedBy(AppDatabaseConstructor::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getBookDao(): BookDao

    abstract fun getUserDao(): UserDao

    abstract fun getBorrowDao(): BorrowDao
}

@Suppress("NO_ACTUAL_FOR_EXPECT", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object AppDatabaseConstructor : RoomDatabaseConstructor<AppDatabase> {
    override fun initialize(): AppDatabase
}

fun getBookDao(appDatabase: AppDatabase) = appDatabase.getBookDao()

fun getUserDao(appDatabase: AppDatabase) = appDatabase.getUserDao()

fun getBorrowDao(appDatabase: AppDatabase) = appDatabase.getBorrowDao()