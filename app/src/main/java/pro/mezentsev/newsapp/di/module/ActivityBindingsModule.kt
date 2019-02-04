package pro.mezentsev.newsapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pro.mezentsev.newsapp.articles.ArticlesActivity
import pro.mezentsev.newsapp.sources.SourcesActivity

@Module
abstract class ActivityBindingsModule {

    @ContributesAndroidInjector
    abstract fun contributeSourcesActivity(): SourcesActivity

    @ContributesAndroidInjector
    abstract fun contributeArticlesActivity(): ArticlesActivity
}