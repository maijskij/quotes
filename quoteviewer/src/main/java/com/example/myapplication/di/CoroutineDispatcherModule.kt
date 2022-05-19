package com.example.myapplication.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherIO

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class CoroutineDispatcherMain

@Module
@InstallIn(SingletonComponent::class)
object CoroutineDispatcherModule {

    @CoroutineDispatcherIO
    @Provides
    fun provideIoCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @CoroutineDispatcherMain
    @Provides
    fun provideMainCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Main
}