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
import pro.mezentsev.newsapp.di.scope.ApplicationScope

@Module
class RepositoryModule {

    @Provides
    @ApplicationScope
    fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository {
        return NewsRepositoryImpl(newsApi, newsDao)
    }

    /**
     * Provides database for [NewsDatabase].
     */
    @Provides
    @ApplicationScope
    fun provideDatabase(context: Context): NewsDatabase {
        return Room.databaseBuilder(
                context,
                NewsDatabase::class.java,
                "news.db")
                .build()
    }

    @Provides
    @ApplicationScope
    fun provideNewsDao(db: NewsDatabase): NewsDao {
        return db.newsDao()
    }
}