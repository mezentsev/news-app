package pro.mezentsev.newsapp

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import pro.mezentsev.newsapp.di.component.DaggerAppComponent
import pro.mezentsev.newsapp.di.module.AppModule

class NewsApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
            DaggerAppComponent.builder().appModule(AppModule(this)).build()
}