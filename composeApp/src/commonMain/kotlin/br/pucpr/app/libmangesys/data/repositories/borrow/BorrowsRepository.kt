package br.pucpr.app.libmangesys.data.repositories.borrow

import br.pucpr.app.libmangesys.data.models.Borrow
import br.pucpr.app.libmangesys.data.models.BorrowWithDetails

interface BorrowsRepository {
    suspend fun truncate()
    suspend fun getList(): List<Borrow>
    suspend fun getListWithDetails(): List<BorrowWithDetails>
    suspend fun insert(item: Borrow)
    suspend fun update(item: Borrow)
    suspend fun delete(itemId: Int?)
    suspend fun find(itemId: Int?): Borrow?
    suspend fun hasBorrowsFromUser(userId: Int?): Boolean
    suspend fun hasBorrowsFromBook(bookId: Int?): Boolean
}