package com.dicoding.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert (favoriteUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser WHERE username = :username")
    fun getFavoriteUserByUsername(username: String): LiveData<FavoriteUser?>


//    @Update
//    fun update (favoriteUser: FavoriteUser)
//
    @Delete
    fun delete(favoriteUser: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser")
    fun getAllFavorite() : LiveData<List<FavoriteUser?>>



}