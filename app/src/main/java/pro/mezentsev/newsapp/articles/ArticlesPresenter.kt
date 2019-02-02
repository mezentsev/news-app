package pro.mezentsev.newsapp.articles

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pro.mezentsev.newsapp.data.NewsRepository
import pro.mezentsev.newsapp.model.Article

class ArticlesPresenter : ArticlesContract.Presenter() {
    private val subscriptions = CompositeDisposable()

    private var category: String? = null
    private var language: String? = null
    private var country: String? = null

    override fun load(count: Int, from: Int) {
        subscriptions.clear()

        val subscribe = NewsRepository.loadArticles(count, from, category, language, country)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ articles: List<Article> ->
                    view?.showArticles(articles, from)
                }, { error ->
                    Log.e(TAG, "Can't get news list", error)
                    view?.showError()
                })

        subscriptions.add(subscribe)
    }

    override fun setArticleParameters(category: String?, language: String?, country: String?) {
        this.category = category
        this.language = language
        this.country = country
    }

    override fun detach() {
        subscriptions.clear()
        super.detach()
    }

    companion object {
        const val TAG = "SourcesPresenter"
    }
}