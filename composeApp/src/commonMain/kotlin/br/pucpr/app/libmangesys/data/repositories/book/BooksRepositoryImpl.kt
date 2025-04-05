package br.pucpr.app.libmangesys.data.repositories.book

import br.pucpr.app.libmangesys.data.models.Book
import br.pucpr.app.libmangesys.data.repositories.borrow.BorrowsRepository
import kotlin.random.Random

class BooksRepositoryImpl(
    private var database: BookDao,
    private var borrowsRepository: BorrowsRepository
): BooksRepository {

    private val mockTitles = listOf(
        "The Great Gatsby",
        "To Kill a Mockingbird",
        "1984",
        "Pride and Prejudice",
        "The Catcher in the Rye"
    )

    private val mockDescriptions = listOf(
        "A novel by F. Scott Fitzgerald",
        "A novel by Harper Lee",
        "A novel by George Orwell",
        "A novel by Jane Austen",
        "A novel by J.D. Salinger",
    )

    private val mockImages = listOf(
        "http://bookcoverarchive.com/wp-content/uploads/2020/07/91bh9jVbRZL.jpg",
        "http://bookcoverarchive.com/wp-content/uploads/2020/07/8ecceb85-a50f-4e5d-a739-bec11f2373c5.jpeg",
        "http://bookcoverarchive.com/wp-content/uploads/2015/06/bloodsplatters1st-uncorrected_txtwithcvf1.png",
        "http://bookcoverarchive.com/wp-content/uploads/amazon/wall_street.jpg",
        "http://bookcoverarchive.com/wp-content/uploads/2016/03/A1Pim60eMZL.jpg"
    )

    override suspend fun truncate() {
        database.truncate()
    }

    override suspend fun getList(): List<Book> = database.getAll()
        .takeIf { it.isNotEmpty() } ?: listOf(1, 2, 3, 4, 5).map { getMockData() }

    override suspend fun find(bookId: Int?): Book? = database.findById(bookId)

    override suspend fun insert(book: Book) {
        if (book.title.isNullOrEmpty()) {
            return
        }

        database.insert(book)
    }

    override suspend fun update(book: Book) {
        if (book.id == null || book.title.isNullOrEmpty()) {
            return
        }

        database.update(book)
    }

    override suspend fun delete(bookId: Int?): Boolean {
        if (bookId == null) {
            return false
        }
        if (borrowsRepository.hasBorrowsFromBook(bookId)) {
            return false
        }

        database.deleteById(bookId)

        return true
    }

    override suspend fun getMockData(): Book {
        val mockPosition = Random.nextInt(0, mockTitles.size - 1)

        return Book(
            title = mockTitles[mockPosition],
            description = mockDescriptions[mockPosition],
            imageUrl = mockImages[mockPosition]
        )
    }
}