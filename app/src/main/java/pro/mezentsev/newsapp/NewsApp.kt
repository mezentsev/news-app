package pro.mezentsev.newsapp

import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pro.mezentsev.newsapp.di.component.DaggerAppComponent
import pro.mezentsev.newsapp.di.module.AppModule

class NewsApp : DaggerApplication() {
    override fun onCreate() {
        super.onCreate()
        AndroidThreeTen.init(this)
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().appModule(AppModule(this)).build()
}