package com.mny.mojito.di.module

import com.mny.mojito.di.qualifier.DefaultDispatcher
import com.mny.mojito.di.qualifier.IoDispatcher
import com.mny.mojito.di.qualifier.MainDispatcher
import com.mny.mojito.di.qualifier.MainImmediateDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * CoroutinesDispatchersModule
 * @author caicai
 * Created on 2021-09-08 11:48
 * Desc:
 */
@InstallIn(SingletonComponent::class)
@Module
object CoroutinesDispatchersModule {

    @DefaultDispatcher
    @Provides
    fun providesDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @IoDispatcher
    @Provides
    fun providesIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun providesMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun providesMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate
}