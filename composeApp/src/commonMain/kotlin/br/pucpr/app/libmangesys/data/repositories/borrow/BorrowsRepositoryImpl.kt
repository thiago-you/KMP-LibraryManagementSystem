package br.pucpr.app.libmangesys.data.repositories.borrow

import br.pucpr.app.libmangesys.data.models.Borrow
import br.pucpr.app.libmangesys.data.models.BorrowWithDetails

class BorrowsRepositoryImpl(
    private var database: BorrowDao
): BorrowsRepository {
    override suspend fun truncate() {
        database.truncate()
    }

    override suspend fun getList(): List<Borrow> = database.getAll()

    override suspend fun find(itemId: Int?): Borrow? = database.findById(itemId)

    override suspend fun insert(item: Borrow) {
        if ((item.bookId ?: 0) <= 0) {
            return
        }
        if ((item.userId ?: 0) <= 0) {
            return
        }

        database.insert(item)
    }

    override suspend fun update(item: Borrow) {
        if (item.id == null) {
            return
        }
        if ((item.bookId ?: 0) <= 0) {
            return
        }
        if ((item.userId ?: 0) <= 0) {
            return
        }

        database.update(item)
    }

    override suspend fun delete(itemId: Int?) {
        if (itemId == null) {
            return
        }

        database.deleteById(itemId)
    }

    override suspend fun hasBorrowsFromBook(bookId: Int?): Boolean {
        if ((bookId ?: 0) <= 0) {
            return false
        }

        return database.getBorrowsFromBook(bookId).isNotEmpty()
    }

    override suspend fun hasBorrowsFromUser(userId: Int?): Boolean {
        if ((userId ?: 0) <= 0) {
            return false
        }

        return database.getBorrowsFromUser(userId).isNotEmpty()
    }

    override suspend fun getListWithDetails(): List<BorrowWithDetails> = database.getAllBorrowsWithDetails()
}