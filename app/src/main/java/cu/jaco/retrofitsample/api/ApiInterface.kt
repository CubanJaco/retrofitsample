package cu.jaco.retrofitsample.api

import cu.jaco.retrofitsample.api.models.Post
import retrofit2.http.*

interface ApiInterface {

    @Headers("Accept: application/json")
    @GET
    suspend fun getPost(
        @Header("Authorization") bearer: String,
        @Url url: String
    ): Post

    @Headers("Accept: application/json")
    @GET("posts/42")
    suspend fun getSpecificPost(
        @Header("Authorization") bearer: String
    ): Post

}