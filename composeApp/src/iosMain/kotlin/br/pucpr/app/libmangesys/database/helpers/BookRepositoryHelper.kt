package br.pucpr.app.libmangesys.database.helpers

import br.pucpr.app.libmangesys.data.repositories.book.BooksRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class BookRepositoryHelper: KoinComponent {
    private val booksRepository: BooksRepository by inject()
    fun getBookRepository(): BooksRepository = booksRepository
}