package pro.mezentsev.newsapp.data

import androidx.annotation.IntRange
import io.reactivex.Single
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source

interface NewsRepository {

    /**
     * Loads list of [Article] from repository. Emits [Single].
     */
    fun loadArticles(sourceId: String,
                     @IntRange(from = 0) count: Int,
                     @IntRange(from = 0) page: Int = 0): Single<List<Article>>

    /**
     * Loads list of [Source] from repository. Emits [Single].
     */
    fun loadSources(): Single<List<Source>>
}