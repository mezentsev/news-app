package pro.mezentsev.newsapp.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import pro.mezentsev.newsapp.articles.ArticlesActivity
import pro.mezentsev.newsapp.di.scope.ActivityScope
import pro.mezentsev.newsapp.sources.SourcesActivity

@Module
abstract class ActivityBindingsModule {

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun contributeSourcesActivity(): SourcesActivity

    @ContributesAndroidInjector
    @ActivityScope
    abstract fun contributeArticlesActivity(): ArticlesActivity
}