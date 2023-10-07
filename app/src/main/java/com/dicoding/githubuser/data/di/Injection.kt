package com.dicoding.githubuser.data.di

import android.content.Context
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.database.FavoriteUserDataBase
import com.dicoding.githubuser.repository.FavoriteUserRepository
import com.dicoding.githubuser.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context):FavoriteUserRepository{
        val apiService = ApiConfig.getApiService()
        val database = FavoriteUserDataBase.getDatabase(context)
        val dao = database.favoriteUserDao()
        val appExecutors = AppExecutors()
        return FavoriteUserRepository.getInstance(apiService,dao,appExecutors)
    }
}