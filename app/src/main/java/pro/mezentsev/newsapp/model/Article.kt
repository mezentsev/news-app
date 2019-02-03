package pro.mezentsev.newsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "article")
data class Article(@ColumnInfo(name = "source") val source: Source,
                   @ColumnInfo(name = "author") val author: String?,
                   @PrimaryKey @ColumnInfo(name = "title") val title: String,
                   @ColumnInfo(name = "description") val description: String?,
                   @ColumnInfo(name = "url") val url: String?,
                   @ColumnInfo(name = "urlToImage") val urlToImage: String?,
                   @ColumnInfo(name = "publishedAt") val publishedAt: String,
                   @ColumnInfo(name = "content") val content: String?)