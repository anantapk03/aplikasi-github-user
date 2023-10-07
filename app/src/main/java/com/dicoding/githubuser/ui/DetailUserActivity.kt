package com.dicoding.githubuser.ui

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.dicoding.githubuser.data.response.DetailUserResponse
import com.dicoding.githubuser.database.FavoriteUser
import com.dicoding.githubuser.databinding.ActivityDetailUserBinding
import com.dicoding.githubuser.repository.FavoriteUserRepository
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding


    val TAB_TITLES = arrayListOf<String>(
        "Followers",
        "Following"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userName = intent?.getStringExtra("USERNAME").toString()
        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        val factory : FavoriteUserModelFactory = FavoriteUserModelFactory.getInstance(this)
        val favoriteUserModel : FavoriteUserModel by viewModels<FavoriteUserModel> {
            factory
        }

        if (userName != null){
            detailViewModel.findDetailUser(userName)
        }
        detailViewModel.getUserDetail().observe(this){
            setDataViewModel(it)
            favoriteUserModel.favoriteUser.username = it.login.toString()
            favoriteUserModel.favoriteUser.avatarUrl = it.avatarUrl

            favoriteUserModel.getFavoriteUser().observe(this){
                binding.toggleFavorite.isChecked = it?.username !=null

                binding.toggleFavorite.setOnClickListener {toggle->
                    if(it?.username == null){
                        favoriteUserModel.setFavoriteUser()
                    } else{
                        favoriteUserModel.deleteFavoriteUser()
                    }
                }
            }

        }

        detailViewModel.isLoading.observe(this){
            showLoading(it)
        }

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager : ViewPager2 = binding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs : TabLayout = binding.tabLayout
        sectionPagerAdapter.userName = userName
        TabLayoutMediator(tabs, viewPager){tab, position ->
            tab.text = TAB_TITLES[position]
        }.attach()

        supportActionBar?.elevation = 0f

    }

    fun setDataViewModel (detailUserResponse: DetailUserResponse?){
        binding.namaPengguna.text = detailUserResponse?.name
        binding.nama.text = detailUserResponse?.login
        binding.following.text = "Following ${detailUserResponse?.following.toString()}"
        binding.followers.text = "Followers ${detailUserResponse?.followers.toString()}"
        Glide.with(this@DetailUserActivity)
            .load(detailUserResponse?.avatarUrl)
            .into(binding.fotoUser)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar2.visibility = View.VISIBLE
        } else {
            binding.progressBar2.visibility = View.INVISIBLE
        }
    }


}