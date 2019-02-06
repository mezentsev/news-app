package pro.mezentsev.newsapp.data.local

import androidx.annotation.IntRange
import androidx.room.*
import io.reactivex.Single
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source

@Dao
interface NewsDao {
    /**
     * Adds list of [Source] or replace on duplicate.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSources(sources: List<Source>)

    /**
     * Gets list of [Source] sorted by ID.
     */
    @Query("SELECT * FROM source ORDER BY id ASC")
    fun getSources(): Single<List<Source>>

    /**
     * Adds list of [Article] or replace on duplicate.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(articles: List<Article>)

    /**
     * Deletes all articles by [Source].
     */
    @Delete
    fun removeArticles(source: Source)

    /**
     * Gets [count] of [Article] by [source] with [page] from the first one.
     */
    @Query("SELECT * FROM article WHERE source = :source ORDER BY publishedAt DESC LIMIT :count OFFSET :page")
    fun getArticles(source: Source,
                    @IntRange(from = 0) count: Int,
                    @IntRange(from = 0) page: Int): Single<List<Article>>
}