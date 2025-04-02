package br.pucpr.app.libmangesys.database.helpers

import br.pucpr.app.libmangesys.data.repositories.user.UserRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class UserRepositoryHelper: KoinComponent {
    private val userRepository: UserRepository by inject()
    fun getUserRepository(): UserRepository = userRepository
}