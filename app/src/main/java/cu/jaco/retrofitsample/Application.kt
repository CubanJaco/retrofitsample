package cu.jaco.retrofitsample

import android.app.Application
import cu.jaco.retrofitsample.di.component.ApplicationComponent
import cu.jaco.retrofitsample.di.component.DaggerApplicationComponent

class Application : Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        initDagger()
    }

    private fun initDagger() {
        appComponent = DaggerApplicationComponent
            .builder()
            .application(this)
            .build()
        appComponent.inject(this)
    }

}