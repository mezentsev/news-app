package pro.mezentsev.newsapp.data

import androidx.annotation.IntRange
import io.reactivex.Single
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source

interface Repository {
    fun loadArticles(@IntRange(from = 0) count: Int,
                     from: Int = 0,
                     category: String? = null,
                     language: String? = null,
                     country: String? = null): Single<List<Article>>

    fun loadSources(): Single<List<Source>>
}