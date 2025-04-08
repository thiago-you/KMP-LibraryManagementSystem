package br.pucpr.app.libmangesys.data.repositories.book

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import br.pucpr.app.libmangesys.data.models.Book

@Dao
interface BookDao {
    @Query("SELECT * FROM book")
    suspend fun getAll(): List<Book>

    @Insert
    suspend fun insert(book: Book)

    @Update
    suspend fun update(book: Book)

    @Query("SELECT * FROM book WHERE id = :bookId")
    suspend fun findById(bookId: Int?): Book?

    @Query("DELETE FROM book WHERE id = :bookId")
    suspend fun deleteById(bookId: Int?)

    @Query("DELETE FROM book")
    suspend fun truncate()
}