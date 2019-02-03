package pro.mezentsev.newsapp.ui

import androidx.annotation.IdRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

abstract class BaseActivity : AppCompatActivity() {
    fun setupActionBar(@IdRes toolbarId: Int, action: ActionBar.() -> Unit) {
        setSupportActionBar(findViewById(toolbarId))
        supportActionBar?.run {
            action()
        }
    }

    fun replaceFragment(@IdRes frameId: Int, fragment: Fragment) {
        supportFragmentManager
                .beginTransaction()
                .replace(frameId, fragment)
                .commit()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}