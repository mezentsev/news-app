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
import java.util.*

class SourcesFragment : BaseFragment<SourcesContract.Presenter>(), SourcesContract.View {
    private lateinit var sourcesAdapter: SourcesAdapter
    private var forceLoad: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sourcesAdapter = SourcesAdapter()
    }

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val isLandscape = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
        val view = inflater.inflate(R.layout.fragment_list, container, false)

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

        presenter.attach(this)
        sourcesAdapter.setSourceClickListener(object : SourceClickListener {
            override fun onSourceObtained(source: Source) {
                presenter.onSourceObtained(source)
            }
        })

        savedInstanceState?.let {
            val list = it.getParcelableArrayList<Source>(BUNDLE_ARRAY_KEY)
            if (list != null) {
                showSources(list)
                forceLoad = false
            }
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        presenter.load(forceLoad)
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
                    .setAction(R.string.error_loading_reload_action) { presenter.load(true) }
                    .show()
        }
    }

    override fun showArticlesUI(sourceId: String) {
        val intent = Intent(context, ArticlesActivity::class.java).apply {
            putExtra(ArticlesActivity.EXTRA_SOURCE_ID, sourceId)
        }
        startActivity(intent)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(BUNDLE_ARRAY_KEY, ArrayList(sourcesAdapter.getSources() ?: listOf()))
    }

    override fun onDestroyView() {
        sourcesAdapter.setSourceClickListener(null)
        presenter.detach()
        super.onDestroyView()
    }

    companion object {
        private const val BUNDLE_ARRAY_KEY = "ARRAY_KEY"

        @JvmStatic
        fun newInstance() = SourcesFragment()
    }
}
