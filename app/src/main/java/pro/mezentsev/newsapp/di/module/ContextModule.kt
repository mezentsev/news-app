package pro.mezentsev.newsapp.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import pro.mezentsev.newsapp.di.scope.ApplicationScope

@Module
class ContextModule {
    @Provides
    @ApplicationScope
    fun provideContext(application: Application): Context = application.applicationContext
}