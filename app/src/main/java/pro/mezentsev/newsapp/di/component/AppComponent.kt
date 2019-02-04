package pro.mezentsev.newsapp.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pro.mezentsev.newsapp.NewsApp
import pro.mezentsev.newsapp.di.module.*
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ContextModule::class,
    ActivityBindingsModule::class,
    RetrofitModule::class,
    RepositoryModule::class
])
interface AppComponent : AndroidInjector<NewsApp> {
    override fun inject(app: NewsApp)
}