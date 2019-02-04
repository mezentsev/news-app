package pro.mezentsev.newsapp.articles

import androidx.annotation.IntRange
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.ui.Contract

interface ArticlesContract {
    interface View : Contract.BaseView {
        fun showArticles(articles: List<Article>, from: Int)
        fun showProgress()
        fun showError()
    }

    abstract class Presenter : Contract.BasePresenter<View>() {
        abstract fun load(@IntRange(from = 0) count: Int = 30,
                          from: Int = 0,
                          sourceId: String,
                          force: Boolean)
    }
}