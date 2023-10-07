package com.dicoding.githubuser.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.githubuser.data.retrofit.ApiService
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.database.FavoriteUserDao
import com.dicoding.githubuser.utils.AppExecutors

class FavoriteUserRepository private constructor(
    private val apiService: ApiService,
    private val favoriteUserDao: FavoriteUserDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<FavoriteUser>>>()

    companion object{
        @Volatile
        private var instance : FavoriteUserRepository? = null
        fun getInstance(
            apiService: ApiService,
            favoriteUserDao: FavoriteUserDao,
            appExecutors: AppExecutors
        ) : FavoriteUserRepository =
            instance ?: synchronized(this){
                instance ?: FavoriteUserRepository(apiService, favoriteUserDao, appExecutors)
            }.also { instance = it }
    }

    fun setFavoriteUser(favoriteUser: FavoriteUser){
        appExecutors.diskIO.execute {
            favoriteUserDao.insert(favoriteUser)
        }
    }

    fun getFavoriteUser(userName : String):LiveData<FavoriteUser?>{
        return favoriteUserDao.getFavoriteUserByUsername(userName)
    }

    fun deleteFavoriteUser(favoriteUser: FavoriteUser){
        appExecutors.diskIO.execute {
            favoriteUserDao.delete(favoriteUser)
        }
    }

    fun getAllListFavoriteUser() : LiveData<List<FavoriteUser?>>{
        return  favoriteUserDao.getAllFavorite()
    }

}