package ru.vitalysizov.dictionaryapp.domain.useCase.base

import io.reactivex.Single

abstract class BaseSingleUseCase<Params, Result> : UseCase() {

    abstract operator fun invoke(): Single<Result>

    abstract operator fun invoke(params: Params): Single<Result>
}