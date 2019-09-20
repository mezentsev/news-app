package pro.mezentsev.newsapp.data

import androidx.annotation.IntRange
import io.reactivex.Single
import pro.mezentsev.newsapp.model.Article

interface ArticlesNewsRepository {
    /**
     * Loads list of [Article] from repository. Emits [Single].
     */
    fun loadArticles(sourceId: String,
                     @IntRange(from = 0) count: Int,
                     @IntRange(from = 0) page: Int): Single<List<Article>>
}