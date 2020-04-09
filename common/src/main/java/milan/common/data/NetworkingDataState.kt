package milan.common.data

sealed class NetworkingDataState {
    object Loading : NetworkingDataState()
    class Success<T>(val item: T) : NetworkingDataState()
    class Error(val throwable: Throwable) : NetworkingDataState()
}
