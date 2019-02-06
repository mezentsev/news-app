package pro.mezentsev.newsapp

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.BeforeClass
import org.junit.Test
import pro.mezentsev.newsapp.data.NewsRepository
import pro.mezentsev.newsapp.model.Source
import pro.mezentsev.newsapp.model.SourceConverter
import pro.mezentsev.newsapp.sources.SourcesContract
import pro.mezentsev.newsapp.sources.SourcesPresenter
import kotlin.test.fail


class SourcesPresenterTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun setup() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
        }
    }

    private val newsRepository = mock<NewsRepository>()

    private val underTest: SourcesContract.Presenter = SourcesPresenter(newsRepository)

    @Test
    fun `do not load sources list without force`() {
        val view = mock<SourcesContract.View>()
        underTest.attach(view)

        underTest.load(false)

        verify(newsRepository, never()).loadSources()
    }

    @Test
    fun `fire showSources for view after success load`() {
        val view = mock<SourcesContract.View>()
        val sources = listOf(
                SourceConverter.toSource("1"),
                SourceConverter.toSource("2")
        )
        val singleSources = Single.just(sources)

        underTest.attach(view)

        whenever(newsRepository.loadSources()).thenReturn(singleSources)

        underTest.load(true)
        singleSources.blockingGet()

        verify(view).showProgress()
        verify(view, never()).showError()
        verify(view).showSources(sources)
    }

    @Test
    fun `fire showError for view on error in repository`() {
        val view = mock<SourcesContract.View>()
        val singleSources = Single.error<List<Source>> { Exception() }

        underTest.attach(view)

        whenever(newsRepository.loadSources()).thenReturn(singleSources)

        try {
            singleSources.blockingGet()
            fail()
        } catch (e: Exception) {
        }

        underTest.load(true)

        verify(view).showProgress()
        verify(view).showError()
        verify(view, never()).showSources(any())
    }

    @Test
    fun `fire showArticlesUI on source obtained`() {
        val view = mock<SourcesContract.View>()
        val source = SourceConverter.toSource("1")

        underTest.attach(view)
        underTest.onSourceObtained(source)

        verify(view).showArticlesUI(source.id)
    }
}
