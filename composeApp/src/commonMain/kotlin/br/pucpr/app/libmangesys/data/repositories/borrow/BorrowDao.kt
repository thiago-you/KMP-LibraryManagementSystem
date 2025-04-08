package br.pucpr.app.libmangesys.data.repositories.borrow

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import br.pucpr.app.libmangesys.data.models.Borrow
import br.pucpr.app.libmangesys.data.models.BorrowWithDetails

@Dao
interface BorrowDao {
    @Query("SELECT * FROM borrow")
    suspend fun getAll(): List<Borrow>

    @Insert
    suspend fun insert(borrow: Borrow)

    @Update
    suspend fun update(borrow: Borrow)

    @Query("SELECT * FROM borrow WHERE id = :borrowId")
    suspend fun findById(borrowId: Int?): Borrow?

    @Query("DELETE FROM borrow WHERE id = :borrowId")
    suspend fun deleteById(borrowId: Int?)

    @Query("DELETE FROM borrow")
    suspend fun truncate()

    @Query("SELECT * FROM borrow WHERE book_id = :bookId")
    suspend fun getBorrowsFromBook(bookId: Int?): List<Borrow>

    @Query("SELECT * FROM borrow WHERE user_id = :userId")
    suspend fun getBorrowsFromUser(userId: Int?): List<Borrow>

    @Transaction
    @Query("SELECT * FROM Borrow")
    suspend fun getAllBorrowsWithDetails(): List<BorrowWithDetails>
}