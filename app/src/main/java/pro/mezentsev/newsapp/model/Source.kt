package pro.mezentsev.newsapp.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

@Entity(tableName = "source")
data class Source(@PrimaryKey @ColumnInfo(name = "id") val id: String,
                  @ColumnInfo(name = "name") val name: String,
                  @ColumnInfo(name = "description") val description: String? = null,
                  @ColumnInfo(name = "url") val url: String? = null,
                  @ColumnInfo(name = "category") val category: String? = null,
                  @ColumnInfo(name = "language") val language: String? = null,
                  @ColumnInfo(name = "country") val country: String? = null)

class SourceConverter {
    companion object {
        @JvmStatic
        @TypeConverter
        fun fromSource(source: Source) = source.id

        @JvmStatic
        @TypeConverter
        fun toSource(sourceId: String) = Source(sourceId, "")
    }
}