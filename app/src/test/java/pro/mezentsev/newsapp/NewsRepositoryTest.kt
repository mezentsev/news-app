package pro.mezentsev.newsapp

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Test
import pro.mezentsev.newsapp.data.NewsRepository
import pro.mezentsev.newsapp.data.NewsRepositoryImpl
import pro.mezentsev.newsapp.data.api.ArticlesResponse
import pro.mezentsev.newsapp.data.api.NewsApi
import pro.mezentsev.newsapp.data.local.NewsDao
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.SourceConverter
import kotlin.test.fail

class NewsRepositoryTest {
    private val newsApi = mock<NewsApi>()
    private val newsDao = mock<NewsDao>()

    private val underTest: NewsRepository = NewsRepositoryImpl(newsApi, newsDao)

    @Test
    fun `load articles only from api and save it to dao`() {
        val sourceId = "1"
        val source = SourceConverter.toSource(sourceId)
        val count = 30
        val page = 0
        val expectedPage = 1

        val articlesFromApi = listOf(
                Article(source, null, "", null, null, null, "", null),
                Article(source, null, "", null, null, null, "", null)
        )
        val articlesResponse = ArticlesResponse("ok", articlesFromApi.size, articlesFromApi)

        whenever(newsApi.getArticles(any(), any(), any())).thenReturn(Single.just(articlesResponse))

        val articles = underTest.loadArticles(sourceId, count, page).blockingGet()

        assertEquals(articlesFromApi.size, articles.size)
        for ((i, a) in articles.withIndex()) {
            assertEquals(articlesFromApi[i], a)
        }

        verify(newsApi).getArticles(eq(sourceId), eq(count), eq(expectedPage))
        verify(newsDao).insertArticles(eq(articlesFromApi))
        verify(newsDao, never()).getArticles(eq(source), eq(count), eq(page))
    }

    @Test
    fun `load articles from dao if api is unavailable`() {
        val sourceId = "1"
        val source = SourceConverter.toSource(sourceId)
        val count = 30
        val page = 0
        val expectedPage = 1

        whenever(newsApi.getArticles(any(), any(), any())).thenReturn(Single.error { Exception() })

        try {
            underTest.loadArticles(sourceId, count, page).blockingGet()
            fail()
        } catch (e: Exception) {
        }

        verify(newsApi).getArticles(eq(sourceId), eq(count), eq(expectedPage))
        verify(newsDao, never()).insertArticles(any())
        verify(newsDao).getArticles(eq(source), eq(count), eq(page))
    }

    @Test
    fun `load sources from dao if api is unavailable`() {
        whenever(newsApi.getSources()).thenReturn(Single.error { Exception() })

        try {
            underTest.loadSources().blockingGet()
            fail()
        } catch (e: Exception) {
        }

        verify(newsApi).getSources(eq(null), eq(null), eq(null))
        verify(newsDao, never()).insertSources(any())
        verify(newsDao).getSources()
    }
}
