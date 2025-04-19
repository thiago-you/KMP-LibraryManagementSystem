package br.pucpr.app.libmangesys

import br.pucpr.app.libmangesys.ui.screens.books.BooksViewModel
import br.pucpr.app.libmangesys.ui.screens.borrows.BorrowsViewModel
import br.pucpr.app.libmangesys.ui.screens.users.UsersViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::BooksViewModel)
    viewModelOf(::UsersViewModel)
    viewModelOf(::BorrowsViewModel)
}