package br.pucpr.app.libmangesys.data.models

import androidx.room.Embedded
import androidx.room.Relation

data class BorrowWithDetails(
    @Embedded val borrow: Borrow,

    @Relation(
        parentColumn = "user_id",
        entityColumn = "id"
    )
    val user: User?,

    @Relation(
        parentColumn = "book_id",
        entityColumn = "id"
    )
    val book: Book?
)