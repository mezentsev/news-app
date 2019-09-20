package pro.mezentsev.newsapp.di.module

import dagger.Module
import dagger.Provides
import pro.mezentsev.newsapp.data.ArticlesNewsRepository
import pro.mezentsev.newsapp.data.NewsRepositoryImpl
import pro.mezentsev.newsapp.data.SourcesNewsRepository
import pro.mezentsev.newsapp.data.api.NewsApi
import pro.mezentsev.newsapp.data.local.NewsDao
import pro.mezentsev.newsapp.di.scope.ApplicationScope

@Module
class RepositoryModule {

    @Provides
    @ApplicationScope
    fun provideSourcesNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): SourcesNewsRepository {
        return NewsRepositoryImpl(newsApi, newsDao)
    }

    @Provides
    @ApplicationScope
    fun provideArticlesNewsRepository(
        newsApi: NewsApi,
        newsDao: NewsDao
    ): ArticlesNewsRepository {
        return NewsRepositoryImpl(newsApi, newsDao)
    }

}