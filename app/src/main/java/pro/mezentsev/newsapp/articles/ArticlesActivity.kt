package pro.mezentsev.newsapp.articles

import android.os.Bundle
import pro.mezentsev.newsapp.R
import pro.mezentsev.newsapp.ui.BaseActivity

class ArticlesActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_with_toolbar)

        setupActionBar(R.id.toolbar) {
            setDisplayHomeAsUpEnabled(true)
            setTitle(R.string.articles_activity_title)
        }

        val extraCategory: String? = intent.getStringExtra(EXTRA_CATEGORY_ARTICLE)
        val extraLanguage: String? = intent.getStringExtra(EXTRA_LANGUAGE_ARTICLE)
        val extraCountry: String? = intent.getStringExtra(EXTRA_COUNTRY_ARTICLE)

        val frameId = R.id.frame
        val articlesFragment = supportFragmentManager.findFragmentById(frameId) as ArticlesFragment?
                ?: ArticlesFragment.newInstance(extraCategory, extraLanguage, extraCountry)
                        .apply {
                            replaceFragment(frameId, this)
                        }

        // Create the presenter
        //SourcesPresenter().apply {
        // Load previously saved state, if available.
        /**if (savedInstanceState != null) {
        currentFiltering = savedInstanceState.getSerializable(CURRENT_FILTERING_KEY)
        as TasksFilterType
        }*/
        //}

        articlesFragment.presenter = ArticlesPresenter().apply {
            setArticleParameters(extraCategory,extraLanguage, extraCountry)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    companion object {
        const val EXTRA_CATEGORY_ARTICLE = "CATEGORY_ARTICLE"
        const val EXTRA_LANGUAGE_ARTICLE = "LANGUAGE_ARTICLE"
        const val EXTRA_COUNTRY_ARTICLE = "COUNTRY_ARTICLE"
    }
}
