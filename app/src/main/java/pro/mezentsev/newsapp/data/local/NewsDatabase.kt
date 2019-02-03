package pro.mezentsev.newsapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source
import pro.mezentsev.newsapp.model.SourceConverter

@Database(entities = [Source::class, Article::class], version = 1)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile private var instance: NewsDatabase? = null

        fun getInstance(context: Context) =
                instance ?: synchronized(this) {
                    instance ?: Room.databaseBuilder(
                            context.applicationContext,
                            NewsDatabase::class.java,
                            "news.db")
                            .build()
                }
    }
}