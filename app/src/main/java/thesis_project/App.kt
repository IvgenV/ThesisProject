package thesis_project

import android.app.Application

class App: Application() {

    override fun onCreate() {
        super.onCreate()
        Dependencies.init(this)
    }

}