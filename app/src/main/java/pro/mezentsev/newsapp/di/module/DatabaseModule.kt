package pro.mezentsev.newsapp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import pro.mezentsev.newsapp.data.local.NewsDatabase
import pro.mezentsev.newsapp.di.scope.ApplicationScope

@Module
class DatabaseModule {
    /**
     * Provides database for [NewsDatabase].
     */
    @Provides
    @ApplicationScope
    fun provideDatabase(context: Context): NewsDatabase {
        return Room.databaseBuilder(
            context,
            NewsDatabase::class.java,
            DATA_BASE_NAME
        )
            .build()
    }

    @Provides
    @ApplicationScope
    fun provideNewsDao(db: NewsDatabase) = db.newsDao()

    companion object {
        const val DATA_BASE_NAME = "news.db"
    }
}