package com.neophron.home.domain.result


sealed class SearchResult {
    class Success(val data: List<String>) : SearchResult()
    class Error(val type: BaseErrorType) : SearchResult()
}