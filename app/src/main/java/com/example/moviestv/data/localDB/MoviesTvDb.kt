package com.example.moviestv.data.localDB

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.moviestv.data.localDB.entities.MovieItem

@Database(entities = [MovieItem::class], version = 1)
abstract class MoviesTvDb: RoomDatabase() {
    abstract fun getMoviesTvDao():MoviesTvDao
}