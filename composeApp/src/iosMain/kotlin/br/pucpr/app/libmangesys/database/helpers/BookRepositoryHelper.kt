package br.pucpr.app.libmangesys.database.helpers

import br.pucpr.app.libmangesys.data.repositories.book.BookRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookRepositoryHelper: KoinComponent {
    private val bookRepository: BookRepository by inject()
    fun getBookRepository(): BookRepository = bookRepository
}