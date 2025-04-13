package br.pucpr.app.libmangesys.ui.screens.borrows

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.app.libmangesys.data.models.Book
import br.pucpr.app.libmangesys.data.models.Borrow
import br.pucpr.app.libmangesys.data.models.BorrowWithDetails
import br.pucpr.app.libmangesys.data.models.User
import br.pucpr.app.libmangesys.data.repositories.book.BooksRepository
import br.pucpr.app.libmangesys.data.repositories.borrow.BorrowsRepository
import br.pucpr.app.libmangesys.data.repositories.user.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BorrowsViewModel(
    private val repository: BorrowsRepository,
    private val usersRepository: UsersRepository,
    private val booksRepository: BooksRepository
) : ViewModel() {
    private val _borrows = MutableStateFlow<List<BorrowWithDetails>>(emptyList())
    val borrows = _borrows.asStateFlow()

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    val books = _books.asStateFlow()

    var borrowEdit by mutableStateOf<Borrow?>(null)
        private set

    var isSaving by mutableStateOf(false)
        private set

    var savedSuccessfully by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _borrows.value = getBorrows()
        }

        viewModelScope.launch(Dispatchers.IO) {
            _users.value = getUsers()
        }

        viewModelScope.launch(Dispatchers.IO) {
            _books.value = getBooks()
        }
    }

    private suspend fun getBorrows() = repository.getListWithDetails()

    private suspend fun getUsers() = usersRepository.getList()

    private suspend fun getBooks() = booksRepository.getList()

    fun edit(borrow: Borrow) {
        borrowEdit = borrow
        isSaving = false
        savedSuccessfully = false
    }

    fun clearBorrowEdit() {
        borrowEdit = null
        isSaving = false
        savedSuccessfully = false
    }

    fun getBorrowToSave(): Borrow? {
        isSaving = false
        savedSuccessfully = false

        return borrowEdit
    }

    fun save(borrow: Borrow) {
        isSaving = true

        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                if (borrow.id != null) {
                    repository.update(borrow)
                } else {
                    repository.insert(borrow)
                }

                _borrows.value = getBorrows()

                borrowEdit = null
                savedSuccessfully = true
                isSaving = false
            }
        }
    }

    fun delete(borrow: Borrow?) {
        isSaving = true

        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                repository.delete(borrow?.id)
                _borrows.value = getBorrows()

                borrowEdit = null
                savedSuccessfully = true
                isSaving = false
            }
        }
    }
}
