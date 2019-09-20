package pro.mezentsev.newsapp.di.component

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import pro.mezentsev.newsapp.NewsApp
import pro.mezentsev.newsapp.di.module.*
import pro.mezentsev.newsapp.di.scope.ApplicationScope

@ApplicationScope
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ContextModule::class,
        ActivityBindingsModule::class,
        RetrofitModule::class,
        DatabaseModule::class
    ]
)
interface AppComponent : AndroidInjector<NewsApp> {
    override fun inject(app: NewsApp)
}