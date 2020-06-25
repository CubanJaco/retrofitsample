package cu.jaco.retrofitsample.api.models

import com.google.gson.annotations.SerializedName

data class RequestError(
    @SerializedName("error")
    val error: String,
    @SerializedName("error_description")
    val error_description: String
)