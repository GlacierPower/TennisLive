package com.glacierpower.tennislive.domain

import javax.inject.Inject

class TennisLiveInteractor @Inject constructor(
    private val tennisLiveRepository: TennisLiveRepository
) {
}