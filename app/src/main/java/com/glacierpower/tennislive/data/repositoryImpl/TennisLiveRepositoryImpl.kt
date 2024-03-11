package com.glacierpower.tennislive.data.repositoryImpl

import com.glacierpower.tennislive.data.service.TennisApiService
import com.glacierpower.tennislive.domain.TennisLiveRepository
import javax.inject.Inject

class TennisLiveRepositoryImpl @Inject constructor(
    private val tennisApiService: TennisApiService
) : TennisLiveRepository {
}