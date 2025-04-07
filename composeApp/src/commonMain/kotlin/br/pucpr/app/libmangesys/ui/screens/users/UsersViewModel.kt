package br.pucpr.app.libmangesys.ui.screens.users

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.pucpr.app.libmangesys.data.models.User
import br.pucpr.app.libmangesys.data.repositories.user.UsersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class UsersViewModel(
    private val repository: UsersRepository,
) : ViewModel() {
    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users = _users.asStateFlow()

    var userEdit by mutableStateOf<User?>(null)
        private set

    var isSaving by mutableStateOf(false)
        private set

    var savedSuccessfully by mutableStateOf(false)
        private set

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _users.value = getUsers()
        }
    }

    private suspend fun getUsers() = repository.getList()

    fun edit(user: User) {
        userEdit = user
        isSaving = false
        savedSuccessfully = false
    }

    fun clearUserEdit() {
        userEdit = null
        isSaving = false
        savedSuccessfully = false
    }

    fun getUserToSave(): User? {
        isSaving = false
        savedSuccessfully = false

        return userEdit
    }

    fun save(user: User) {
        isSaving = true

        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                if (user.id != null) {
                    repository.update(user)
                } else {
                    repository.insert(user)
                }

                _users.value = getUsers()

                userEdit = null
                savedSuccessfully = true
                isSaving = false
            }
        }
    }

    fun delete(user: User?) {
        isSaving = true

        viewModelScope.launch(Dispatchers.IO) {
            runBlocking {
                repository.delete(user?.id)
                _users.value = getUsers()

                userEdit = null
                savedSuccessfully = true
                isSaving = false
            }
        }
    }
}
