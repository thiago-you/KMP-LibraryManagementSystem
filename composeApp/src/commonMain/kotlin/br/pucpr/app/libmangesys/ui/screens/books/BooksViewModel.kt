package br.pucpr.app.libmangesys.ui.screens.books

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.app.libmangesys.data.models.Book
import br.pucpr.app.libmangesys.data.repositories.book.BooksRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BooksViewModel(
    private val repository: BooksRepository,
) : ViewModel() {
    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    var bookEdit by mutableStateOf<Book?>(null)
        private set

    var isSaving by mutableStateOf(false)
        private set

    var savedSuccessfully by mutableStateOf(false)
        private set

    var deleteError by mutableStateOf<Boolean?>(null)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _books.value = getBooks()
        }
    }

    private suspend fun getBooks() = repository.getList()

    fun edit(book: Book) {
        bookEdit = book
        isSaving = false
        savedSuccessfully = false
    }

    fun clearBookEdit() {
        bookEdit = null
        isSaving = false
        savedSuccessfully = false
    }

    fun getBookToSave(): Book? {
        isSaving = false
        savedSuccessfully = false

        return bookEdit
    }

    fun save(book: Book) {
        isSaving = true

        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                if (book.id != null) {
                    repository.update(book)
                } else {
                    repository.insert(book)
                }

                _books.value = getBooks()

                bookEdit = null
                savedSuccessfully = true
                isSaving = false
            }
        }
    }

    fun delete(book: Book?) {
        isSaving = true

        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                deleteError = repository.delete(book?.id) == false

                _books.value = getBooks()

                bookEdit = null
                savedSuccessfully = true
                isSaving = false
            }
        }
    }
}
