package pro.mezentsev.newsapp.sources

import android.util.Log
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import pro.mezentsev.newsapp.data.NewsRepository
import pro.mezentsev.newsapp.model.Source

class SourcesPresenter constructor(private val newsRepository: NewsRepository): SourcesContract.Presenter() {
    private val subscriptions = CompositeDisposable()

    override fun load() {
        Log.d(TAG, "Start loading")
        view?.showProgress()

        subscriptions.clear()

        val subscribe = newsRepository.loadSources()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ sources: List<Source> ->
                    view?.showSources(sources)
                }, { error ->
                    Log.e(TAG, "Can't get sources", error)
                    view?.showError()
                })

        subscriptions.add(subscribe)
    }

    override fun onSourceObtained(source: Source) {
        view?.showArticlesUI(source.id)
                ?: Log.d(TAG, "No attached view to process $source")
    }

    override fun detach() {
        subscriptions.clear()
        super.detach()
    }

    companion object {
        private const val TAG = "SourcesPresenter"
    }
}