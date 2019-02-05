package pro.mezentsev.newsapp.data

import androidx.annotation.IntRange
import io.reactivex.Single
import pro.mezentsev.newsapp.data.api.NewsApi
import pro.mezentsev.newsapp.data.local.NewsDao
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source
import pro.mezentsev.newsapp.model.SourceConverter
import java.util.concurrent.TimeUnit

class NewsRepositoryImpl constructor(private val newsApi: NewsApi,
                                     private val newsDao: NewsDao) : NewsRepository {

    override fun loadSources(): Single<List<Source>> {
        return newsApi.getSources()
                .timeout(3, TimeUnit.SECONDS)
                .toObservable()
                .flatMapIterable { it.sources }
                .toList()
                .doAfterSuccess { newsDao.insertSources(it) }
                .onErrorResumeNext {
                    newsDao.getSources()
                            .filter { !it.isEmpty() }
                            .toSingle()
                }
    }

    override fun loadArticles(sourceId: String,
                              @IntRange(from = 0) count: Int,
                              @IntRange(from = 0) page: Int): Single<List<Article>> {
        return newsApi.getArticles(sourceId, count, page)
                .timeout(3, TimeUnit.SECONDS)
                .toObservable()
                .flatMapIterable { it.articles }
                .toList()
                .doAfterSuccess {
                    newsDao.removeArticles(it[0].source)
                    newsDao.insertArticles(it)
                }
                .onErrorResumeNext {
                    newsDao.getArticles(SourceConverter.toSource(sourceId), count, page )
                            .filter { !it.isEmpty() }
                            .toSingle()
                }
    }
}