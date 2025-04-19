package br.pucpr.app.libmangesys

import br.pucpr.app.libmangesys.data.repositories.book.BooksRepository
import br.pucpr.app.libmangesys.data.repositories.book.BooksRepositoryImpl
import br.pucpr.app.libmangesys.data.repositories.borrow.BorrowsRepository
import br.pucpr.app.libmangesys.data.repositories.borrow.BorrowsRepositoryImpl
import br.pucpr.app.libmangesys.data.repositories.getBookDao
import br.pucpr.app.libmangesys.data.repositories.getBorrowDao
import br.pucpr.app.libmangesys.data.repositories.getUserDao
import br.pucpr.app.libmangesys.data.repositories.user.UsersRepository
import br.pucpr.app.libmangesys.data.repositories.user.UsersRepositoryImpl
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
            provideDatabaseModule,
            provideRepositoryModule
        )
    }

val provideRepositoryModule = module {
    singleOf(::BooksRepositoryImpl).bind(BooksRepository::class)
    singleOf(::UsersRepositoryImpl).bind(UsersRepository::class)
    singleOf(::BorrowsRepositoryImpl).bind(BorrowsRepository::class)
}

val provideDatabaseModule = module {
    single { getBookDao(get()) }
    single { getUserDao(get()) }
    single { getBorrowDao(get()) }
}