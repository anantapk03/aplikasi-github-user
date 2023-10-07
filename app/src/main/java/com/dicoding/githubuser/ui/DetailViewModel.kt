package com.dicoding.githubuser.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.repository.FavoriteUserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val detailUser = MutableLiveData<DetailUserResponse>()
    private val _isLoading = MutableLiveData <Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    companion object{
        const val TAG= "DetailUserModel"
    }

    fun findDetailUser(userName:String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(userName)
        client.enqueue(object : Callback<DetailUserResponse>{
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    detailUser.postValue(response.body())
                } else{
                    val errorBody = response.errorBody()
                    Log.e(TAG, "onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
               _isLoading.value = false
                Log.e(TAG, "onFailure : ${t.message.toString()}")
            }

        })
    }

    fun getUserDetail():LiveData<DetailUserResponse>{
        return detailUser
    }

}