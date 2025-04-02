package br.pucpr.app.libmangesys.database.helpers

import br.pucpr.app.libmangesys.data.repositories.user.UsersRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepositoryHelper: KoinComponent {
    private val usersRepository: UsersRepository by inject()
    fun getUserRepository(): UsersRepository = usersRepository
}