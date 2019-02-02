package pro.mezentsev.newsapp.sources

import android.os.Bundle
import pro.mezentsev.newsapp.R
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

        // Create the presenter
        //SourcesPresenter().apply {
            // Load previously saved state, if available.
            /**if (savedInstanceState != null) {
            currentFiltering = savedInstanceState.getSerializable(CURRENT_FILTERING_KEY)
            as TasksFilterType
            }*/
        //}

        sourcesFragment.presenter = SourcesPresenter()
    }
}
