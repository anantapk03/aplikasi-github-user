package com.dicoding.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.databinding.ActivityListFavoriteUserBinding

class ListFavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory : FavoriteUserModelFactory = FavoriteUserModelFactory.getInstance(this)
        val favoriteUserModel : FavoriteUserModel by viewModels<FavoriteUserModel> { factory }

        val layoutManager = LinearLayoutManager(this)
        binding.listFavoriteUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.listFavoriteUser.addItemDecoration(itemDecoration)


        favoriteUserModel.getAllFavorite().observe(this){
            showLoading(true)
            setFavoriteUsers(it)
            showLoading(false)
        }

    }

    private fun setFavoriteUsers(favoriteUser: List<FavoriteUser?>){
        val adapter = ListUserFavoriteAdapter()
        adapter.submitList(favoriteUser)
        binding.listFavoriteUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.INVISIBLE
        }
    }

}