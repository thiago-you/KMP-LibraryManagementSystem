package br.pucpr.app.libmangesys

import br.pucpr.app.libmangesys.ui.screens.books.BooksViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

actual val viewModelModule = module {
    viewModelOf(::BooksViewModel)
}