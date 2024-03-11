package com.glacierpower.tennislive.di

import com.glacierpower.tennislive.domain.TennisLiveInteractor
import com.glacierpower.tennislive.domain.TennisLiveRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun provideTennisLiveInteractor(
        tennisLiveRepository: TennisLiveRepository
    ): TennisLiveInteractor = TennisLiveInteractor(tennisLiveRepository)
}