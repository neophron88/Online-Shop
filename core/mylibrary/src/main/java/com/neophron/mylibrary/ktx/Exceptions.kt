package com.neophron.mylibrary.ktx

inline fun <T> tryCatch(
    action: () -> T,
    onError: (e: Exception) -> T
) = try {
    action()
} catch (e: Exception) {
    onError(e)
}

