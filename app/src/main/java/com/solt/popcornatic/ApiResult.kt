package com.solt.popcornatic

sealed interface ApiResult {
    class Success<T>(val data:T ):ApiResult
    sealed interface Failure :ApiResult{
        class NetworkFailure( val exception:Exception):Failure
        class ApiFailure(val exception :Exception):Failure
    }


}