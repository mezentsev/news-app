package pro.mezentsev.newsapp

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.BeforeClass
import org.junit.Test
import pro.mezentsev.newsapp.articles.ArticlesContract
import pro.mezentsev.newsapp.articles.ArticlesPresenter
import pro.mezentsev.newsapp.data.NewsRepository
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.SourceConverter
import kotlin.test.fail


class ArticlesPresenterTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }
    }

    private val newsRepository = mock<NewsRepository>()

    private val underTest: ArticlesContract.Presenter = ArticlesPresenter(newsRepository)

    @Test
    fun `do not load articles without force`() {
        val view = mock<ArticlesContract.View>()
        val count = 30
        val sourceId = "1"
        underTest.attach(view)

        underTest.load(count, sourceId, false)

        verify(newsRepository, never()).loadArticles(eq(sourceId), eq(count), eq(0))
    }

    @Test
    fun `fire showArticles for view after success load`() {
        val view = mock<ArticlesContract.View>()
        val count = 30
        val sourceId = "1"
        val source = SourceConverter.toSource(sourceId)
        val articles = listOf(
                Article(source, null, "1", null, null, null, "", null),
                Article(source, null, "2", null, null, null, "", null),
                Article(source, null, "3", null, null, null, "", null)
        )
        val singleArticles = Single.just(articles)

        underTest.attach(view)

        whenever(newsRepository.loadArticles(any(), any(), any())).thenReturn(singleArticles)
        singleArticles.blockingGet()

        underTest.load(count, sourceId, true)

        verify(view).showProgress()
        verify(view, never()).showError()
        verify(view).showArticles(articles)
    }

    @Test
    fun `fire showArticles for view after success load filtered empty title`() {
        val view = mock<ArticlesContract.View>()
        val count = 30
        val sourceId = "1"
        val source = SourceConverter.toSource(sourceId)
        val articles = listOf(
                Article(source, null, "1", null, null, null, "", null),
                Article(source, null, "", null, null, null, "", null)
        )
        val singleArticles = Single.just(articles)

        underTest.attach(view)

        whenever(newsRepository.loadArticles(any(), any(), any())).thenReturn(singleArticles)

        underTest.load(count, sourceId, true)
        singleArticles.blockingGet()

        verify(view).showProgress()
        verify(view, never()).showError()
        verify(view).showArticles(listOf(articles[0]))
    }

    @Test
    fun `fire showError for view on error in repository`() {
        val view = mock<ArticlesContract.View>()
        val count = 30
        val sourceId = "1"
        val singleArticles = Single.error<List<Article>> { Exception() }

        underTest.attach(view)

        whenever(newsRepository.loadArticles(any(), any(), any())).thenReturn(singleArticles)

        try {
            singleArticles.blockingGet()
            fail()
        } catch (e: Exception) {
        }

        underTest.load(count, sourceId, true)

        verify(view).showProgress()
        verify(view).showError()
        verify(view, never()).showArticles(any())
    }
}
