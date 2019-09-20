package pro.mezentsev.newsapp.di.module

import dagger.Module
import dagger.Provides
import pro.mezentsev.newsapp.data.NewsRepository
import pro.mezentsev.newsapp.data.NewsRepositoryImpl
import pro.mezentsev.newsapp.data.api.NewsApi
import pro.mezentsev.newsapp.data.local.NewsDao
import pro.mezentsev.newsapp.di.scope.ApplicationScope

@Module
class RepositoryModule {

    @Provides
    @ApplicationScope
    fun provideNewsRepository(newsApi: NewsApi, newsDao: NewsDao): NewsRepository {
        return NewsRepositoryImpl(newsApi, newsDao)
    }

}