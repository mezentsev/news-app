package pro.mezentsev.newsapp.articles

import androidx.annotation.IntRange
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.model.Source
import pro.mezentsev.newsapp.ui.Contract

interface ArticlesContract {
    interface View : Contract.BaseView {
        /**
         * Displays [articles] in View.
         */
        fun showArticles(articles: List<Article>)

        /**
         * Shows loading progress bar.
         */
        fun showProgress()

        /**
         * Shows error message.
         */
        fun showError()
    }

    abstract class Presenter : Contract.BasePresenter<View>() {
        /**
         * Loads list of [Article].
         * @param count     maximum count of loaded articles
         * @param sourceId  ID of [Source]
         * @param force     force reload articles
         */
        abstract fun load(@IntRange(from = 0) count: Int = 30,
                          sourceId: String,
                          force: Boolean)
    }
}