package cu.jaco.retrofitsample.api.safeapicall

sealed class ResultWrapper<T, E> {
    data class Success<T>(val value: T): ResultWrapper<T, Nothing>()
    data class GenericError<E>(val code: Int? = null, val error: E? = null): ResultWrapper<Nothing, E>()
    class NetworkError<E> : ResultWrapper<Nothing, E>()
}