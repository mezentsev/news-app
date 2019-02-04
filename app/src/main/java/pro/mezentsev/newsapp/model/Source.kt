package pro.mezentsev.newsapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter

/**
 * Provides model for [source](https://newsapi.org/docs/endpoints/sources).
 */
@Entity(tableName = "source")
data class Source(@PrimaryKey @ColumnInfo(name = "id") val id: String,
                  @ColumnInfo(name = "name") val name: String,
                  @ColumnInfo(name = "description") val description: String? = null,
                  @ColumnInfo(name = "url") val url: String? = null,
                  @ColumnInfo(name = "category") val category: String? = null,
                  @ColumnInfo(name = "language") val language: String? = null,
                  @ColumnInfo(name = "country") val country: String? = null) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString() ?: "",
            parcel.readString() ?: "",
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(category)
        parcel.writeString(language)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Source> {
        override fun createFromParcel(parcel: Parcel): Source {
            return Source(parcel)
        }

        override fun newArray(size: Int): Array<Source?> {
            return arrayOfNulls(size)
        }
    }
}


class SourceConverter {
    companion object {
        @JvmStatic
        @TypeConverter
        fun fromSource(source: Source) = source.id

        @JvmStatic
        @TypeConverter
        fun toSource(sourceId: String = "") = Source(sourceId, "")
    }
}