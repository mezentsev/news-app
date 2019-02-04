package pro.mezentsev.newsapp.model

import androidx.room.TypeConverter

object SourceConverter {
    @JvmStatic
    @TypeConverter
    fun fromSource(source: Source) = source.id

    @JvmStatic
    @TypeConverter
    fun toSource(sourceId: String = "") = Source(sourceId, "")
}