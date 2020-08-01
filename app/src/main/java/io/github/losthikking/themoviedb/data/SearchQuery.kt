package io.github.losthikking.themoviedb.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class SearchQuery(
        @PrimaryKey val query: String,
        @ColumnInfo val date: Date
)