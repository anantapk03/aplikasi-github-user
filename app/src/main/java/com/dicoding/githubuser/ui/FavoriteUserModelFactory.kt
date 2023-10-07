package com.dicoding.githubuser.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.data.di.Injection
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.repository.FavoriteUserRepository

class FavoriteUserModelFactory private constructor(private val favoriteUserRepository: FavoriteUserRepository): ViewModelProvider.NewInstanceFactory(){
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavoriteUserModel::class.java)){
            return FavoriteUserModel(favoriteUserRepository) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel Class : "+ modelClass.name)
    }

    companion object{
        @Volatile
        private var instance : FavoriteUserModelFactory? = null
        fun getInstance(context: Context): FavoriteUserModelFactory =
            instance ?: synchronized(this){
                instance ?: FavoriteUserModelFactory(Injection.provideRepository(context))
            }.also { instance = it }
    }
}