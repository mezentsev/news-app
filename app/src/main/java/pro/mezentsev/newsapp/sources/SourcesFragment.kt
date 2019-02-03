package pro.mezentsev.newsapp.sources

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_list.view.*
import pro.mezentsev.newsapp.R
import pro.mezentsev.newsapp.articles.ArticlesActivity
import pro.mezentsev.newsapp.model.Source
import pro.mezentsev.newsapp.sources.adapter.SourceClickListener
import pro.mezentsev.newsapp.sources.adapter.SourcesAdapter
import pro.mezentsev.newsapp.ui.BaseFragment

class SourcesFragment : BaseFragment<SourcesContract.Presenter>(), SourcesContract.View {
    private lateinit var sourcesAdapter: SourcesAdapter

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        presenter.attach(this)

        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        sourcesAdapter = SourcesAdapter()
        view.list.apply {
            adapter = sourcesAdapter
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

    override fun showSources(sources: List<Source>) {
        hideProgress()
        sourcesAdapter.setSources(sources)
    }

    override fun showProgress() {
        view?.let {
            it.progress_view.visibility = VISIBLE
        }
    }

    private fun hideProgress() {
        view?.let {
            it.progress_view.visibility = GONE
        }
    }

    override fun showError() {
        hideProgress()
        view?.let {
            Snackbar
                    .make(it, R.string.error_loading_sources, Snackbar.LENGTH_INDEFINITE)
                    .setAction(R.string.error_loading_reload_action) { presenter.load() }
                    .show()
        }
    }

    override fun showArticlesUI(category: String?, language: String?, country: String?) {
        val intent = Intent(context, ArticlesActivity::class.java).apply {
            putExtra(ArticlesActivity.EXTRA_CATEGORY_ARTICLE, category)
            putExtra(ArticlesActivity.EXTRA_LANGUAGE_ARTICLE, language)
            putExtra(ArticlesActivity.EXTRA_COUNTRY_ARTICLE, country)
        }
        startActivity(intent)
    }

    override fun onResume() {
        super.onResume()

        sourcesAdapter.setSourceClickListener(object: SourceClickListener {
            override fun onSourceObtained(source: Source) {
                presenter.onSourceObtained(source)
            }
        })

        presenter.load()
    }

    override fun onDestroyView() {
        sourcesAdapter.setSourceClickListener(null)
        presenter.detach()
        super.onDestroyView()
    }

    companion object {
        @JvmStatic
        fun newInstance() = SourcesFragment()
    }
}
