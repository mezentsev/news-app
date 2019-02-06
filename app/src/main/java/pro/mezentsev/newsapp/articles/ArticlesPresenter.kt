package pro.mezentsev.newsapp.articles

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import pro.mezentsev.newsapp.data.NewsRepository
import pro.mezentsev.newsapp.model.Article
import kotlin.math.floor

class ArticlesPresenter constructor(private val newsRepository: NewsRepository) : ArticlesContract.Presenter() {
    private lateinit var sourceId: String
    private var count: Int = 10
    private var page: Int = 1
    private var isLoading = false
    private var isLastPage = false

    private val subscriptions = CompositeDisposable()

    override fun load(sourceId: String, force: Boolean) {
        this.sourceId = sourceId

        if (!force || isLoading || isLastPage) {
            return
        }

        subscriptions.clear()

        isLoading = true
        view?.showProgress()

        subscriptions.add(
                getSubscriber({ articles ->
                    isLastPage = articles.size < count
                    view?.showArticles(articles)
                    page += 1
                }, {
                    view?.showError()
                })
        )
    }

    override fun setOffset(offset: Int) {
        page += floor(maxOf(0, offset).toFloat() / count).toInt()
    }


    override fun detach() {
        subscriptions.clear()
        super.detach()
    }

    private fun getSubscriber(onSuccess: (List<Article>) -> Unit, onError: (Throwable) -> Unit): Disposable {
        return newsRepository.loadArticles(sourceId, count, page)
                .map {
                    it.filter { article ->
                        article.title.isNotEmpty()
                    }
                }
                .doFinally {
                    isLoading = false
                }
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ articles: List<Article> ->
                    onSuccess(articles)
                }, {
                    onError(it)
                })
    }
}