package cu.jaco.retrofitsample.di.component

import cu.jaco.retrofitsample.Application
import cu.jaco.retrofitsample.MainActivity
import cu.jaco.retrofitsample.di.modules.ApplicationModule
import cu.jaco.retrofitsample.di.modules.ResModule
import cu.jaco.retrofitsample.di.modules.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        ResModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder

        fun build(): ApplicationComponent
    }

    fun inject(app: Application)

    fun inject(activity: MainActivity)

}