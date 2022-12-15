package thesis_project.di

import dagger.Module
import dagger.Provides
import thesis_project.MainApp

@Module
class AppModule {

    @Provides
    fun provideContext(application: MainApp) = application.applicationContext

}