package pro.mezentsev.newsapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source
import pro.mezentsev.newsapp.model.SourceConverter

@Database(entities = [Source::class, Article::class], version = 1)
@TypeConverters(SourceConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao
}