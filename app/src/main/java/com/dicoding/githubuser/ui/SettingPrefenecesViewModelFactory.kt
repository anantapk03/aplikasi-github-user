package com.dicoding.githubuser.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.data.SettingPreferences

class SettingPrefenecesViewModelFactory (private val pref : SettingPreferences) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SettingThemeViewModel::class.java)){
            return SettingThemeViewModel(pref) as T
        }
        throw java.lang.IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}