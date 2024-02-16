package core

sealed class MyResult{
    data object Loading:MyResult()
    data class  Success<T>(val data:T):MyResult()
    data class  Failed<T>(val data:T?,val error:String):MyResult()
}