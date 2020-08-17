package ru.vitalysizov.dictionaryapp.domain.useCase.base

import io.reactivex.Single

abstract class SingleWithParamsUseCase<Params, Result> : BaseSingleUseCase<Params, Result>() {

    override fun invoke(): Single<Result> {
        throw IllegalAccessException("You must call method invoke() with params")
    }
}