package pro.mezentsev.newsapp.data

import androidx.annotation.IntRange
import io.reactivex.Single
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source

interface NewsRepository {
    fun loadArticles(@IntRange(from = 0) count: Int,
                     from: Int,
                     sourceId: String): Single<List<Article>>

    fun loadSources(): Single<List<Source>>
}