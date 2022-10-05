package com.example.moviestv.di

import android.app.Application
import androidx.room.Room
import com.example.moviestv.data.localDB.MoviesTvDao
import com.example.moviestv.data.localDB.MoviesTvDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomDbModule {

    @Provides
    @Singleton
    fun provideMoviesTvDb(
        application: Application
    ): MoviesTvDb {
        val moviesTvDb by lazy {
            Room.databaseBuilder(
                application,MoviesTvDb::class.java,"MoviesTvDb"
            ).build()
        }
        return  moviesTvDb
    }

    fun provideMoviesTvDao(db: MoviesTvDb): MoviesTvDao {
        return db.getMoviesTvDao()
    }
}