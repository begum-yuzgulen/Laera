package com.yuzgulen.laera.domain.usecases

interface UseCase<in T> {
    fun execute(callback: T): Any
}