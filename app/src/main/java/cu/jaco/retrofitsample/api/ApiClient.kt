package cu.jaco.retrofitsample.api

import cu.jaco.retrofitsample.api.models.RequestError
import cu.jaco.retrofitsample.api.models.Post
import cu.jaco.retrofitsample.api.safeapicall.SafeApiCall
import cu.jaco.retrofitsample.preferences.Preferences
import cu.jaco.retrofitsample.api.safeapicall.ResultWrapper
import javax.inject.Inject

class ApiClient @Inject constructor(
    private val api: ApiInterface,
    private val preferences: Preferences
) : SafeApiCall() {

    suspend fun getPost(post: String): ResultWrapper<Post, RequestError> =
        safeApiCall(RequestError::class.java) {
            api.getPost(
                "SomeToken ${preferences.token}",
                "posts/$post"
            )
        }

    suspend fun getSpecificPost(): ResultWrapper<Post, RequestError> =
        safeApiCall(RequestError::class.java) {
            api.getSpecificPost(
                "SomeToken ${preferences.token}"
            )
        }

}