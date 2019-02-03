package pro.mezentsev.newsapp.sources

import pro.mezentsev.newsapp.model.Source
import pro.mezentsev.newsapp.ui.Contract

interface SourcesContract {
    interface View : Contract.BaseView {
        fun showSources(sources: List<Source>)
        fun showError()
        fun showProgress()
        fun showArticlesUI(sourceId: String)
    }

    abstract class Presenter : Contract.BasePresenter<View>() {
        abstract fun load()
        abstract fun onSourceObtained(source: Source)
    }
}