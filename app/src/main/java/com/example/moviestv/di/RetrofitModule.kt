package com.example.moviestv.di

import androidx.compose.ui.Modifier
import com.example.moviestv.data.reomteApi.MoviesTvApi
import com.example.moviestv.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
       val interceptor=HttpLoggingInterceptor()
        interceptor.level=HttpLoggingInterceptor.Level.BODY
        val client=OkHttpClient.Builder().addInterceptor(interceptor).build()


        val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        }
        return retrofit
    }

    fun provideMoviesTvApi(retrofit: Retrofit): MoviesTvApi{
        return retrofit.create(MoviesTvApi::class.java)
    }

}