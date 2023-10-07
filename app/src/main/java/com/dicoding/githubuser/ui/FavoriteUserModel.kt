package com.dicoding.githubuser.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.repository.FavoriteUserRepository

class FavoriteUserModel (private val userFavoriteUserRepository: FavoriteUserRepository):ViewModel() {

    var favoriteUser = FavoriteUser()

    fun setFavoriteUser() = userFavoriteUserRepository.setFavoriteUser(favoriteUser)
    fun getFavoriteUser() = userFavoriteUserRepository.getFavoriteUser(favoriteUser.username)
    fun deleteFavoriteUser() = userFavoriteUserRepository.deleteFavoriteUser(favoriteUser)

    fun getAllFavorite() = userFavoriteUserRepository.getAllListFavoriteUser()


}