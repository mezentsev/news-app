package pro.mezentsev.newsapp.data.api

import android.util.Log
import io.reactivex.Single
import okhttp3.OkHttpClient
import pro.mezentsev.newsapp.BuildConfig
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface NewsApiOrg {

    @GET("top-headlines")
    fun getArticles(@Query("pageSize") count: Int = 20,
                    @Query("page") from: Int = 0,
                    @Query("category") category: String = "general",
                    @Query("language") language: String? = null,
                    @Query("country") country: String? = null): Single<ArticlesResponse>

    @GET("sources")
    fun getSources(@Query("category") category: String? = null,
                   @Query("language") language: String? = null,
                   @Query("country") country: String? = null): Single<SourcesResponse>

    companion object Factory {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val TAG = "Retrofit"

        fun create(): NewsApiOrg {
            val httpClient = OkHttpClient.Builder()
            httpClient.addInterceptor { chain ->
                val original = chain.request()
                val originalHttpUrl = original.url()

                val url = originalHttpUrl.newBuilder()
                        .addQueryParameter("apiKey", BuildConfig.NEWSAPI_ORG_TOKEN)
                        .build()

                val requestBuilder = original.newBuilder()
                        .url(url)

                val request = requestBuilder.build()

                Log.d(TAG, request.url().toString())
                chain.proceed(request)
            }

            val retrofit = retrofit2.Retrofit.Builder()
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .client(httpClient.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .baseUrl(BASE_URL)
                    .build()

            return retrofit.create(NewsApiOrg::class.java)
        }
    }
}