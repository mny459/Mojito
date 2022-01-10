package com.mny.mojito.di.qualifier

import javax.inject.Qualifier

/**
 * CoroutinesQualifiers
 * @author caicai
 * Created on 2021-09-08 11:47
 * Desc:
 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class DefaultDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class IoDispatcher

@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class MainDispatcher

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class MainImmediateDispatcher