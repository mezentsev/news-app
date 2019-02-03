package pro.mezentsev.newsapp.data

import android.content.Context
import android.util.Log
import androidx.annotation.IntRange
import io.reactivex.Single
import pro.mezentsev.newsapp.data.api.NewsApiOrg
import pro.mezentsev.newsapp.data.local.NewsDao
import pro.mezentsev.newsapp.data.local.NewsDatabase
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source
import pro.mezentsev.newsapp.model.SourceConverter
import java.util.concurrent.TimeUnit

class NewsRepositoryImpl constructor(
        private val context: Context,
        private val newsApi: NewsApiOrg = NewsApiOrg.create(),
        private val newsDao: NewsDao = NewsDatabase.getInstance(context).newsDao()) : NewsRepository {

    override fun loadSources(): Single<List<Source>> {
        return newsApi.getSources()
                .timeout(3, TimeUnit.SECONDS)
                .toObservable()
                .flatMapIterable { it.sources }
                .toList()
                .doAfterSuccess { newsDao.insertAllSources(it) }
                .onErrorResumeNext { ex ->
                    Log.d(TAG, "Problem with api. Trying to load from dao", ex)
                    newsDao.getSources()
                            .filter { !it.isEmpty() }
                            .toSingle()
                }
    }

    override fun loadArticles(@IntRange(from = 0) count: Int,
                              from: Int,
                              sourceId: String): Single<List<Article>> {
        return newsApi.getArticles(count, from, sourceId)
                .timeout(3, TimeUnit.SECONDS)
                .toObservable()
                .flatMapIterable { it.articles }
                .toList()
                .doAfterSuccess {
                    newsDao.removeAllArticles(it[0].source)
                    newsDao.insertAllArticles(it)
                }
                .onErrorResumeNext { ex ->
                    Log.d(TAG, "Problem with api. Trying to load from dao", ex)
                    newsDao.getArticles(count, from, SourceConverter.toSource(sourceId))
                            .filter { !it.isEmpty() }
                            .toSingle()
                }
    }

    companion object {
        const val TAG = "NewsRepositoryImpl"
    }
}