package br.pucpr.app.libmangesys

import br.pucpr.app.libmangesys.data.repositories.book.BookRepository
import br.pucpr.app.libmangesys.data.repositories.book.BookRepositoryImpl
import br.pucpr.app.libmangesys.data.repositories.borrow.BorrowRepository
import br.pucpr.app.libmangesys.data.repositories.borrow.BorrowRepositoryImpl
import br.pucpr.app.libmangesys.data.repositories.getBookDao
import br.pucpr.app.libmangesys.data.repositories.getBorrowDao
import br.pucpr.app.libmangesys.data.repositories.getRoomDatabase
import br.pucpr.app.libmangesys.data.repositories.getUserDao
import br.pucpr.app.libmangesys.data.repositories.user.UserRepository
import br.pucpr.app.libmangesys.data.repositories.user.UserRepositoryImpl
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.KoinAppDeclaration
import org.koin.dsl.bind
import org.koin.dsl.module

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

expect fun platformModule(): Module

fun initKoin(config: KoinAppDeclaration? = null) =
    startKoin {
        config?.invoke(this)
        modules(
            platformModule(),
            provideRepositoryModule,
            provideDatabaseModule
        )
    }

val provideRepositoryModule = module {
    singleOf(::BookRepositoryImpl).bind(BookRepository::class)
    singleOf(::UserRepositoryImpl).bind(UserRepository::class)
    singleOf(::BorrowRepositoryImpl).bind(BorrowRepository::class)
}

val provideDatabaseModule = module {
    single { getRoomDatabase(get()) }
    single { getBookDao(get()) }
    single { getUserDao(get()) }
    single { getBorrowDao(get()) }
}