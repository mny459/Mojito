package com.mny.mojito.widget.pkg.di

import com.mny.mojito.widget.pkg.data.local.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

/**
 * Desc: 抽象类的使用 @Binds
 */
@InstallIn(SingletonComponent::class)
@Module
abstract class WidgetModule {
//    @Singleton
//    @Binds
//    abstract fun bindLoginRepository(repository: UserRepositoryImpl): UserRepository
//
//    @Singleton
//    @Binds
//    abstract fun bindWanRepository(repository: WanRepositoryImpl): WanRepository

    /**
     * 其他的使用 @Provides
     */
    @InstallIn(SingletonComponent::class)
    @Module
    object WidgetDataModule {

        @Provides
        @Singleton
        fun provideWidgetDb(): WidgetDatabase = WidgetDatabase.db()

        @Provides
        @Singleton
        fun provideMottoDao(db: WidgetDatabase): MottoDao = db.mottoDao()

        @Provides
        @Singleton
        fun provideTodayDao(db: WidgetDatabase): TodayDao = db.todayDao()

        @Provides
        @Singleton
        fun provideTimerNoteDao(db: WidgetDatabase): TimerNoteDao = db.timerNoteDao()

        @Provides
        @Singleton
        fun provideLauncherDao(db: WidgetDatabase): LauncherDao = db.launcherDao()

        @Singleton  // 永远提供相同实例
        @Provides
        fun providesGlobalCoroutineScope(): CoroutineScope {
            // 当提供 CoroutineScope 实例时，执行如下代码
            return CoroutineScope(SupervisorJob() + Dispatchers.Default)
        }
    }
}
