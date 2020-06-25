package cu.jaco.retrofitsample.di.modules

import android.content.Context
import cu.jaco.retrofitsample.Application
import cu.jaco.retrofitsample.MainActivity
import dagger.Module
import dagger.Provides

@Module
class ApplicationModule {
    @Provides
    fun providersApplication(app: Application): Context = app
}