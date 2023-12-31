package com.dicoding.githubuser.data.retrofit

import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.data.response.GithubResponse
import com.dicoding.githubuser.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @GET("search/users")
//    @Headers("Authorization: token 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")
    fun getUserGithub(
        @Query("q") id:String
    ) : Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(
        @Path("username") username: String
    ) : Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(
        @Path("username") username: String
    ) : Call<List<ItemsItem>>

    @GET("users/{username}/following")
    fun getFollowing(
        @Path("username") username: String
    ) : Call<List<ItemsItem>>




}