package br.pucpr.app.libmangesys.ui.screens.books

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.app.libmangesys.data.models.Book
import br.pucpr.app.libmangesys.data.repositories.book.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class BooksViewModel(
    private val booksRepository: BooksRepository
) : ViewModel() {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _books.value = getBooks()
        }
    }

    private suspend fun getBooks() = booksRepository.getList()
}