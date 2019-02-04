package pro.mezentsev.newsapp.di.module

import android.app.Application
import dagger.Module
import dagger.Provides
import pro.mezentsev.newsapp.di.scope.ApplicationScope

@Module
class AppModule(private val application: Application) {
    @Provides
    @ApplicationScope
    fun provideApplication(): Application = application
}