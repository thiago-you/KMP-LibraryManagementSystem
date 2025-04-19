package br.pucpr.app.libmangesys.data.repositories.book

import br.pucpr.app.libmangesys.data.models.Book

interface BooksRepository  {
    suspend fun truncate()
    suspend fun getList(): List<Book>
    suspend fun insert(book: Book)
    suspend fun update(book: Book)
    suspend fun delete(bookId: Int?): Boolean
    suspend fun find(bookId: Int?): Book?
    suspend fun getMockData(mockIndex: Int): Book
}