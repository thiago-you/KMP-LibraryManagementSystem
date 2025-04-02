package br.pucpr.app.libmangesys.ui.screens.books

import androidx.lifecycle.ViewModel
import br.pucpr.app.libmangesys.data.repositories.book.BooksRepository

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {
    suspend fun getBooks() = booksRepository.getList()
}