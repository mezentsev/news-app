package pro.mezentsev.newsapp.data

import androidx.annotation.IntRange
import io.reactivex.Single
import pro.mezentsev.newsapp.data.api.NewsApiOrg
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source

object NewsRepository: Repository {
    private val newsApi = NewsApiOrg.create()

    override fun loadSources(): Single<List<Source>> {
        return newsApi.getSources()
                .toObservable()
                .flatMapIterable { it.sources }
                //.doOnNext {  }
                .toList()
    }

    override fun loadArticles(@IntRange(from = 0) count: Int,
                              from: Int,
                              category: String?,
                              language: String?,
                              country: String?): Single<List<Article>> {
        return newsApi.getArticles(count, from, language = language, country = country)
                .toObservable()
                .flatMapIterable { it.articles }
                //.doOnNext {  }
                .toList()
    }
}