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

        val frameId = R.id.frame
        val sourcesFragment = supportFragmentManager.findFragmentById(frameId) as SourcesFragment?
                ?: SourcesFragment.newInstance().apply {
                    replaceFragment(frameId, this)
                }

        //todo inject
        sourcesFragment.presenter = SourcesPresenter(NewsRepositoryImpl(this))
    }
}
