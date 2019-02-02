package pro.mezentsev.newsapp.articles

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list.view.*

import pro.mezentsev.newsapp.R
import pro.mezentsev.newsapp.articles.ArticlesActivity.Companion.EXTRA_CATEGORY_ARTICLE
import pro.mezentsev.newsapp.articles.ArticlesActivity.Companion.EXTRA_COUNTRY_ARTICLE
import pro.mezentsev.newsapp.articles.ArticlesActivity.Companion.EXTRA_LANGUAGE_ARTICLE
import pro.mezentsev.newsapp.articles.adapter.ArticlesAdapter
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.ui.BaseFragment

class ArticlesFragment : BaseFragment<ArticlesContract.Presenter>(), ArticlesContract.View {
    private lateinit var articlesAdapter: ArticlesAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter.attach(this)

        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        articlesAdapter = ArticlesAdapter(view.context)
        view.list.apply {
            adapter = articlesAdapter
            if (isLandscape) {
                layoutManager = GridLayoutManager(context, 2)
                addItemDecoration(DividerItemDecoration(context, LinearLayout.HORIZONTAL))
            } else {
                layoutManager = LinearLayoutManager(context)
            }
            addItemDecoration(DividerItemDecoration(context, LinearLayout.VERTICAL))
        }

        return view
    }

    override fun showArticles(articles: List<Article>, from: Int) {
        articlesAdapter.setArticles(articles)
    }

    override fun showError() {
        val root = view ?: return
        Snackbar.make(root, R.string.error_loading_articles, Snackbar.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        presenter.load()
    }

    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance(extraCategory: String?,
                        extraLanguage: String?,
                        extraCountry: String?) =
                ArticlesFragment().apply {
                    arguments = Bundle().apply {
                        putString(EXTRA_CATEGORY_ARTICLE, extraCategory)
                        putString(EXTRA_LANGUAGE_ARTICLE, extraLanguage)
                        putString(EXTRA_COUNTRY_ARTICLE, extraCountry)
                    }
                }
    }
}
