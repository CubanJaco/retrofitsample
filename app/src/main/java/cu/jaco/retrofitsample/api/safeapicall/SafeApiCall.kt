package cu.jaco.retrofitsample.api.safeapicall

import com.google.gson.Gson
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

open class SafeApiCall(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    @Suppress("UNCHECKED_CAST")
    suspend fun <T, E> safeApiCall(
        errorClass: Class<E>,
        apiCall: suspend () -> T
    ): ResultWrapper<T, E> {
        return withContext(dispatcher) {
            try {
                ResultWrapper.Success(apiCall.invoke()) as ResultWrapper<T, E>
            } catch (throwable: Throwable) {
                when (throwable) {
                    is IOException -> ResultWrapper.NetworkError<E>() as ResultWrapper<T, E>
                    is HttpException -> {
                        val code = throwable.code()
                        val errorResponse = convertErrorBody(errorClass, throwable)
                        ResultWrapper.GenericError(code, errorResponse) as ResultWrapper<T, E>
                    }
                    else -> {
                        ResultWrapper.GenericError(null, null) as ResultWrapper<T, E>
                    }
                }
            }
        }
    }

    private fun <E> convertErrorBody(errorClass: Class<E>, throwable: HttpException): E? {
        return try {
            throwable
                .response()
                ?.errorBody()
                ?.string()
                ?.let {
                    Gson().fromJson(it, errorClass)
                }
        } catch (exception: Exception) {
            null
        }
    }

}