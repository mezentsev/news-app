package pro.mezentsev.newsapp.data.local

import androidx.room.*
import io.reactivex.Single
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source

@Dao
interface NewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertSource(source: Source)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAllSources(sources: List<Source>)

    @Query("SELECT * FROM source ORDER BY id ASC")
    fun getSources(): Single<List<Source>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticle(article: Article)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAllArticles(articles: List<Article>)

    @Delete
    fun removeAllArticles(source: Source)

    @Query("SELECT * FROM article WHERE source = :source LIMIT :count OFFSET :from")
    fun getArticles(count: Int,
                    from: Int,
                    source: Source): Single<List<Article>>
}