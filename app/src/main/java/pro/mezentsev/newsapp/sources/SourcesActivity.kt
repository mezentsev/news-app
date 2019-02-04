package pro.mezentsev.newsapp.sources

import android.os.Bundle
import pro.mezentsev.newsapp.R
import pro.mezentsev.newsapp.data.NewsRepositoryImpl
import pro.mezentsev.newsapp.ui.BaseActivity

class SourcesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_toolbar)

        setupActionBar(R.id.toolbar) {
            setTitle(R.string.sources_activity_title)
        }

        //todo inject
        val sourcePresenter = SourcesPresenter(NewsRepositoryImpl(this))
        val frameId = R.id.frame
        (supportFragmentManager.findFragmentById(frameId) as SourcesFragment?)?.apply { presenter = sourcePresenter }
                ?: SourcesFragment.newInstance().apply {
                    presenter = sourcePresenter
                    replaceFragment(frameId, this)
                }
    }
}
