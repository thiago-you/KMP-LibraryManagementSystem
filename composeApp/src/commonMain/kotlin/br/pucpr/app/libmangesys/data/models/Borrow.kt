package br.pucpr.app.libmangesys.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Borrow(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "book_id") var bookId: Int? = null,
    @ColumnInfo(name = "user_id") var userId: Int? = null,
)