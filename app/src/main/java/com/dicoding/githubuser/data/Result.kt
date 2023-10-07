package com.dicoding.githubuser.data

sealed class Result <out R> private constructor(){
    data class Success<out T>(val data: T): com.dicoding.githubuser.data.Result<T>()
    data class Error(val error : String) : com.dicoding.githubuser.data.Result<Nothing>()
    object Loading : com.dicoding.githubuser.data.Result<Nothing>()
}