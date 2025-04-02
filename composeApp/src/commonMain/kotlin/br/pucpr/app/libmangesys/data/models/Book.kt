package br.pucpr.app.libmangesys.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Book(
    @PrimaryKey(autoGenerate = true) var id: Int? = null,
    @ColumnInfo(name = "title") var title: String? = null,
    @ColumnInfo(name = "description") var description: String? = null,
    @ColumnInfo(name = "image_url") var imageUrl: String? = null
)