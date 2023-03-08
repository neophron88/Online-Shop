package com.neophron.feature.contract.common

interface DependencyProvider {

    val dependency: Any
}

inline fun <reified T> Any.extractDependency(): T {
    val provider = this
    if (provider !is DependencyProvider)
        error("The ${this::class.simpleName} did not implement DependencyProvider")

    val dependency: Any = provider.dependency
    if (dependency !is T)
        error("DependencyProvider's dependency field did not implement ${T::class.simpleName}")

    return dependency
}