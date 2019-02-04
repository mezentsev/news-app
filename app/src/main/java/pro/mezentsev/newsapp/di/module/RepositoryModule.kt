package pro.mezentsev.newsapp.di.module

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import pro.mezentsev.newsapp.data.NewsRepository
import pro.mezentsev.newsapp.data.NewsRepositoryImpl
import pro.mezentsev.newsapp.data.api.NewsApi
import pro.mezentsev.newsapp.data.local.NewsDao
import pro.mezentsev.newsapp.data.local.NewsDatabase

@Module
class RepositoryModule {

    @Provides
    fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository {
        return NewsRepositoryImpl(newsApi, newsDao)
    }

    /**
     * Provides database for [NewsDatabase].
     */
    @Provides
    fun provideDatabase(context: Context): NewsDatabase {
        return Room.databaseBuilder(
                context,
                NewsDatabase::class.java,
                "news.db")
                .build()
    }

    @Provides
    fun provideNewsDao(db: NewsDatabase): NewsDao {
        return db.newsDao()
    }
}