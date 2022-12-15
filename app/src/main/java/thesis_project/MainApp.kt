package thesis_project

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import thesis_project.di.DaggerAppComponent


class MainApp : DaggerApplication() {

    companion object {
        lateinit var instance: MainApp
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }


    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.factory().create(this)
    }

}