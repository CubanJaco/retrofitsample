package cu.jaco.retrofitsample.di.modules

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import cu.jaco.retrofitsample.di.annotations.ViewModelKey
import cu.jaco.retrofitsample.ui.post.PostViewModel
import cu.jaco.retrofitsample.viewmodels.ViewModelFactory
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(PostViewModel::class) // PROVIDE YOUR OWN MODELS HERE
    internal abstract fun bindTransportListViewModel(transportListViewModel: PostViewModel): ViewModel

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}