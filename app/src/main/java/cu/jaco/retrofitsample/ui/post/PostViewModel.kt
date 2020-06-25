package cu.jaco.retrofitsample.ui.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import cu.jaco.retrofitsample.api.ApiClient
import javax.inject.Inject

class PostViewModel @Inject constructor(
    private val api: ApiClient
) : ViewModel() {

    fun getPost(post: String = "42") = liveData {
        emit(
            api.getPost(post)
        )
    }

    fun getSpecificPost() = liveData {
        emit(
            api.getSpecificPost()
        )
    }

}