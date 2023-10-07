package com.dicoding.githubuser.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.GithubResponse
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel (){
    private val _user = MutableLiveData <List<ItemsItem>>()
    val user :  LiveData<List<ItemsItem>> = _user

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    companion object {
        private const val TAG = "MainViewModel"
        private const val USER_ID = "A"
    }

    init {
        findUser(USER_ID)
    }

    fun findUser (id : String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUserGithub(id ?: USER_ID)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _user.value = response.body()?.items
                } else {
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }
            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }
        })
    }





}