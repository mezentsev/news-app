package pro.mezentsev.newsapp.articles

import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
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
import pro.mezentsev.newsapp.articles.ArticlesActivity.Companion.EXTRA_SOURCE_ID
import pro.mezentsev.newsapp.articles.adapter.ArticlesAdapter
import pro.mezentsev.newsapp.model.Article
import pro.mezentsev.newsapp.ui.BaseFragment

class ArticlesFragment : BaseFragment<ArticlesContract.Presenter>(), ArticlesContract.View {
    private lateinit var articlesAdapter: ArticlesAdapter
    private var forceLoad: Boolean = true

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

        savedInstanceState?.let {
            val list = it.getParcelableArrayList<Article>(BUNDLE_ARRAY_KEY)
            if (!list.isNullOrEmpty()) {
                showArticles(list, 0)
                forceLoad = false
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        load(forceLoad)
    }

    override fun showArticles(articles: List<Article>, from: Int) {
        hideProgress()
        articlesAdapter.setArticles(articles)

        view?.let {
            if (articles.isEmpty()) {
                Snackbar
                        .make(it, R.string.error_no_articles_found, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.error_no_articles_found_back_text) { activity?.onBackPressed() }
                        .show()
            }
        }
    }

    override fun showProgress() {
        view?.let {
            it.progress_view.visibility = View.VISIBLE
        }
    }

    private fun hideProgress() {
        view?.let {
            it.progress_view.visibility = View.GONE
        }
    }

    override fun showError() {
        hideProgress()
        view?.let {
            Snackbar
                    .make(it, R.string.error_loading_articles, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.error_loading_reload_action) { load(true) }
                    .show()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(BUNDLE_ARRAY_KEY, ArrayList(articlesAdapter.getArticles()))
    }


    override fun onDestroyView() {
        presenter.detach()
        super.onDestroyView()
    }

    private fun load(force: Boolean) {
        arguments?.getString(EXTRA_SOURCE_ID)?.let {
            presenter.load(sourceId = it, force = force)
        } ?: Log.d(TAG, "Can't get source id")
    }

    companion object {
        private const val TAG = "ArticlesFragment"
        private const val BUNDLE_ARRAY_KEY = "ARRAY_KEY"

        @JvmStatic
        fun newInstance(sourceId: String?) =
                ArticlesFragment().apply {
                    arguments = Bundle().apply {
                        putString(EXTRA_SOURCE_ID, sourceId)
                    }
                }
    }
}
