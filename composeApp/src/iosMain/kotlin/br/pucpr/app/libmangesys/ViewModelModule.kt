package br.pucpr.app.libmangesys

import br.pucpr.app.libmangesys.ui.screens.books.BooksViewModel
import br.pucpr.app.libmangesys.ui.screens.users.UsersViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val viewModelModule = module {
    singleOf(::BooksViewModel)
    singleOf(::UsersViewModel)
}