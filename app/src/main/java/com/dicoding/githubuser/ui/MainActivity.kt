package com.dicoding.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.githubuser.R
import com.dicoding.githubuser.data.SettingPreferences
import com.dicoding.githubuser.data.dataStore
import com.dicoding.githubuser.data.response.GithubResponse
import com.dicoding.githubuser.data.response.ItemsItem
import com.dicoding.githubuser.data.retrofit.ApiConfig
import com.dicoding.githubuser.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), Toolbar.OnMenuItemClickListener {

    private lateinit var binding : ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        val mainViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)
        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingPreferencesViewModel = ViewModelProvider(this, SettingPrefenecesViewModelFactory(pref)).get(
            SettingThemeViewModel::class.java
        )

        settingPreferencesViewModel.getThemeSettings().observe(this){ isDarkModeActive : Boolean ->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }

        }


        with(binding){
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, i, keyEvent ->
                    searchBar.text = searchView.text
                    var id = searchView.text.toString()
                    searchView.hide()
                    //Toast.makeText(this@MainActivity,searchView.text, Toast.LENGTH_SHORT).show()
                    mainViewModel.findUser(id)
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUser.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUser.addItemDecoration(itemDecoration)


        //OBSERVE LIVEDATA
        mainViewModel.user.observe(this){ itemsItem ->
            setGithubUser(itemsItem)
        }

        //OBSERVE SHOWLOADING
        mainViewModel.isLoading.observe(this){
            showLoading(it)
        }

        binding.searchBar.setOnMenuItemClickListener { menuFavoriteUser ->
            when(menuFavoriteUser.itemId){
                R.id.menu1 -> {
                    val intent = Intent(this, ListFavoriteUserActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu2 -> {
                    val intent = Intent(this, SettingThemeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }

    }

    private fun setGithubUser(itemsItem: List<ItemsItem>) {
        val adapter = ListUserAdapter()
        adapter.submitList(itemsItem)
        binding.rvUser.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.INVISIBLE
        }
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return false
    }

}