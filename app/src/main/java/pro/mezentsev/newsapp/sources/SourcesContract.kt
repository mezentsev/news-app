package pro.mezentsev.newsapp.sources

import pro.mezentsev.newsapp.articles.ArticlesContract
import pro.mezentsev.newsapp.model.Source
import pro.mezentsev.newsapp.ui.Contract

interface SourcesContract {
    interface View : Contract.BaseView {
        /**
         * Displays [Source] in View
         */
        fun showSources(sources: List<Source>)

        /**
         * Shows loading progress bar.
         */
        fun showProgress()

        /**
         * Shows error message.
         */
        fun showError()

        /**
         * Navigates to [ArticlesContract.View] with defined [Source] ID.
         */
        fun showArticlesUI(sourceId: String)
    }

    abstract class Presenter : Contract.BasePresenter<View>() {
        /**
         * Loads list of [Source].
         * @param force force reload sources
         */
        abstract fun load(force: Boolean)

        /**
         * Source has been received from user action.
         */
        abstract fun onSourceObtained(source: Source)
    }
}