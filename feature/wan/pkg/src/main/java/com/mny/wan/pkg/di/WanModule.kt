package com.mny.wan.pkg.di

import com.mny.wan.pkg.data.remote.service.WanService
import com.mny.wan.pkg.data.repository.WanRepositoryImpl
import com.mny.wan.pkg.domain.repository.WanRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * Desc: 抽象类的使用 @Binds
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class WanModule {

    @Singleton
    @Binds
    abstract fun bindWanRepository(repository: WanRepositoryImpl): WanRepository

    /**
     * 其他的使用 @Provides
     */
    @InstallIn(SingletonComponent::class)
    @Module
    object WanDataModule {

        @Provides
        @Singleton
        fun provideWanService(retrofit: Retrofit): WanService {
            return retrofit.create(WanService::class.java)
        }
    }
}
