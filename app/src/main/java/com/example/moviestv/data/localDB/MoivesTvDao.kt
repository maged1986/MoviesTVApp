package com.example.moviestv.data.localDB

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.REPLACE
import androidx.room.Query
import com.example.moviestv.data.localDB.entities.MovieItem


@Dao
interface MoviesTvDao {

    //this is dao interface
    @Insert(onConflict = REPLACE)
    fun insertMovie(movieItem: MovieItem?)

    @Query("delete from movie_items where id =:id")
    fun deleteMovie(id: Int?)

    @Query("DELETE FROM movie_items")
    fun deleteAll()

    @Query("select * from movie_items")
    fun getMovies(): LiveData<List<MovieItem?>?>?
}