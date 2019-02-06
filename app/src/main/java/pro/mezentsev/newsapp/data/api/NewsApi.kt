package pro.mezentsev.newsapp.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    /**
     * Gets list of [Article] from [top-headlines](https://newsapi.org/docs/endpoints/top-headlines).
     * @param sourceId  ID of [Source]
     * @param count     maximum count of loaded articles
     * @param page      articles page
     */
    @GET("everything")
    fun getArticles(@Query("sources") sourceId: String,
                    @Query("pageSize") count: Int,
                    @Query("page") page: Int): Single<ArticlesResponse>

    /**
     * Gets list of [Source] from [sources](https://newsapi.org/docs/endpoints/sources).
     * @param category  display news of this category. Possible options: business, entertainment,
     *                  general, health, science, sports, technology. Default: all categories
     * @param language  articles in a specific language. Possible options: ar, de, en, es, fr, he,
     *                  it, nl, no, pt, ru, se, ud, zh. Default: all languages
     * @param country   articles in a specific country. Default: all countries
     */
    @GET("sources")
    fun getSources(@Query("category") category: String? = null,
                   @Query("language") language: String? = null,
                   @Query("country") country: String? = null): Single<SourcesResponse>
}