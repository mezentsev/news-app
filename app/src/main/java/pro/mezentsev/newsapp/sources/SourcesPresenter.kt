package pro.mezentsev.newsapp.sources

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pro.mezentsev.newsapp.data.NewsRepository
import pro.mezentsev.newsapp.model.Source

class SourcesPresenter : SourcesContract.Presenter() {

    private val subscriptions = CompositeDisposable()

    override fun load() {
        subscriptions.clear()

        val subscribe = NewsRepository.loadSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ sources: List<Source> ->
                    view?.showSources(sources)
                }, { error ->
                    Log.e(TAG, "Can't get source list", error)
                    view?.showError()
                })

        subscriptions.add(subscribe)
    }

    override fun onSourceObtained(source: Source) {
        view?.showArticlesUI(source.category, source.language, source.country)
                ?: Log.d(TAG, "No attached view to process $source")
    }

    override fun detach() {
        subscriptions.clear()
        super.detach()
    }

    companion object {
        const val TAG = "SourcesPresenter"
    }
}