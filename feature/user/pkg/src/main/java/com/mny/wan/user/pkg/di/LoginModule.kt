package com.mny.wan.user.pkg.di

import com.mny.wan.user.pkg.data.remote.service.UserService
import com.mny.wan.user.pkg.data.repository.CoinRepositoryImpl
import com.mny.wan.user.pkg.data.repository.UserRepositoryImpl
import com.mny.wan.user.pkg.domain.repository.CoinRepository
import com.mny.wan.user.pkg.domain.repository.UserRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
abstract class LoginModule {

    @Singleton
    @Binds
    abstract fun bindUserRepository(repository: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    abstract fun bindCoinRepository(repository: CoinRepositoryImpl): CoinRepository

    @InstallIn(SingletonComponent::class)
    @Module
    object WanDataModule {

        @Provides
        @Singleton
        fun provideWanService(retrofit: Retrofit): UserService {
            return retrofit.create(UserService::class.java)
        }
    }
}
