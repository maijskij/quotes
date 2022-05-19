package com.example.myapplication.features.quotes.di

import com.example.myapplication.features.quotes.data.FavQsRepositoryImpl
import com.example.myapplication.features.quotes.domain.FavQsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class QuotesModule {

    @Binds
    @ViewModelScoped
    abstract fun bindFeatureRepository(featureRepositoryImpl: FavQsRepositoryImpl): FavQsRepository

}