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
    fun getAll(): List<Borrow>

    @Insert
    fun insert(borrow: Borrow)

    @Update
    fun update(borrow: Borrow)

    @Query("SELECT * FROM borrow WHERE id = :borrowId")
    fun findById(borrowId: Int?): Borrow?

    @Query("DELETE FROM borrow WHERE id = :borrowId")
    fun deleteById(borrowId: Int?)

    @Query("DELETE FROM borrow")
    fun truncate()

    @Query("SELECT * FROM borrow WHERE book_id = :bookId")
    fun getBorrowsFromBook(bookId: Int?): List<Borrow>

    @Query("SELECT * FROM borrow WHERE user_id = :userId")
    fun getBorrowsFromUser(userId: Int?): List<Borrow>

    @Transaction
    @Query("SELECT * FROM Borrow")
    fun getAllBorrowsWithDetails(): List<BorrowWithDetails>
}