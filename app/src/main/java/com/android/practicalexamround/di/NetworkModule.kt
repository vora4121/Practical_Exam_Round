package com.android.practicalexamround.di

import com.android.practicalexamround.api.APIs
import com.android.practicalexamround.utils.Constant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constant.BaseUrl)
            .addConverterFactory(GsonConverterFactory.create())
    }

    @Singleton
    @Provides
    fun providesAPI(retrofitBuilder: Retrofit.Builder): APIs {
        return retrofitBuilder.build().create(APIs::class.java)
    }
}