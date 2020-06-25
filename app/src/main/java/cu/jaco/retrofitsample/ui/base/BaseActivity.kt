package cu.jaco.retrofitsample.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cu.jaco.retrofitsample.Application
import cu.jaco.retrofitsample.di.component.ApplicationComponent

abstract class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        inject((applicationContext as Application).appComponent)
        super.onCreate(savedInstanceState)
    }

    protected abstract fun inject(appComponent: ApplicationComponent)

}