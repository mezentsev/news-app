package pro.mezentsev.newsapp.articles

import android.os.Bundle
import pro.mezentsev.newsapp.R
import pro.mezentsev.newsapp.data.NewsRepositoryImpl
import pro.mezentsev.newsapp.ui.BaseActivity

class ArticlesActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_toolbar)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.articles_activity_title)
        }

        val sourceId: String? = intent.getStringExtra(EXTRA_SOURCE_ID)

        //todo inject
        val articlesPresenter = ArticlesPresenter(NewsRepositoryImpl(this))
        val frameId = R.id.frame
        (supportFragmentManager.findFragmentById(frameId) as ArticlesFragment?)?.apply { presenter = articlesPresenter }
                ?: ArticlesFragment.newInstance(sourceId).apply {
                    presenter = articlesPresenter
                    replaceFragment(frameId, this)
                }
    }

    companion object {
        const val EXTRA_SOURCE_ID = "SOURCE_ID"
    }
}
