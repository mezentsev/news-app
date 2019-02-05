package pro.mezentsev.newsapp.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter
/**
 * Provides model for [article](https://newsapi.org/docs/endpoints/sources).
 */
@Entity(tableName = "article")
data class Article(@ColumnInfo(name = "source") val source: Source,
                   @ColumnInfo(name = "author") val author: String?,
                   @PrimaryKey @ColumnInfo(name = "title") val title: String,
                   @ColumnInfo(name = "description") val description: String?,
                   @ColumnInfo(name = "url") val url: String?,
                   @ColumnInfo(name = "urlToImage") val urlToImage: String?,
                   @ColumnInfo(name = "publishedAt") val publishedAt: String,
                   @ColumnInfo(name = "content") val content: String?): Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readParcelable(Source::class.java.classLoader) ?: SourceConverter.toSource(),
            parcel.readString(),
            parcel.readString() ?: "",
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString() ?: "",
            parcel.readString())

    fun getDate(): String = try {
        ZonedDateTime.parse(publishedAt)
                .withZoneSameInstant(ZoneId.systemDefault())
                .format(DateTimeFormatter.ofPattern("HH:mm dd.MM.yyyy"))
    } catch (ex: Exception) {
        publishedAt
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(source, flags)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Article> {
        override fun createFromParcel(parcel: Parcel): Article {
            return Article(parcel)
        }

        override fun newArray(size: Int): Array<Article?> {
            return arrayOfNulls(size)
        }
    }
}
