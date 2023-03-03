package com.neophron.feature.contract

interface DependencyProvider {

    val dependency: Any
}

inline fun <reified R, reified T> R.extractDependency(): T {
    val provider = this
    if (provider !is DependencyProvider)
        error("The Application did not implement DependencyProvider")

    val dependency: Any = provider.dependency
    if (dependency !is T)
        error("The top level dagger component did not implement ${T::class.simpleName}")

    return dependency
}