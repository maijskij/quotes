package com.example.myapplication.di

import com.example.myapplication.features.quotes.data.network.FavQsApiImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object NetworkLayerModule {

    @Provides
    fun provideFavQsNetworkApi() = FavQsApiImpl.instance
}