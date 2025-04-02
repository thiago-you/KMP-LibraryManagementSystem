package br.pucpr.app.libmangesys.data.repositories.user

import br.pucpr.app.libmangesys.data.models.User

interface UsersRepository {
    suspend fun truncate()
    suspend fun getList(): List<User>
    suspend fun insert(user: User)
    suspend fun update(user: User)
    suspend fun delete(userId: Int?): Boolean
    suspend fun find(userId: Int?): User?
}