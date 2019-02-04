package pro.mezentsev.newsapp.data.api

import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("top-headlines")
    fun getArticles(@Query("pageSize") count: Int = 30,
                    @Query("page") from: Int = 0,
                    @Query("sources") sourceId: String): Single<ArticlesResponse>

    @GET("sources")
    fun getSources(@Query("category") category: String? = null,
                   @Query("language") language: String? = null,
                   @Query("country") country: String? = null): Single<SourcesResponse>
}