package pro.mezentsev.newsapp.di.module

import android.util.Log
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import pro.mezentsev.newsapp.BuildConfig
import pro.mezentsev.newsapp.data.api.NewsApi
import pro.mezentsev.newsapp.di.scope.ApplicationScope
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

@Module
class RetrofitModule {
    /**
     * Provides instance for [NewsApi].
     */
    @Provides
    @ApplicationScope
    fun provideNewsApi(retrofit: Retrofit): NewsApi {
        return retrofit.create(NewsApi::class.java)
    }

    /**
     * Provides retrofit instance.
     */
    @Provides
    @ApplicationScope
    fun provideRetrofit(): Retrofit {
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

        return retrofit2.Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
    }

    companion object {
        private const val BASE_URL = "https://newsapi.org/v2/"
        private const val TAG = "Retrofit"
    }
}