package com.dicoding.githubuser.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.dicoding.githubuser.data.SettingPreferences
import com.dicoding.githubuser.data.dataStore
import com.dicoding.githubuser.databinding.ActivitySettingThemeBinding

class SettingThemeActivity : AppCompatActivity() {

    private lateinit var binding : ActivitySettingThemeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingThemeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val pref = SettingPreferences.getInstance(application.dataStore)
        val settingPreferencesViewModel = ViewModelProvider(this, SettingPrefenecesViewModelFactory(pref) ).get(
            SettingThemeViewModel::class.java
        )

        settingPreferencesViewModel.getThemeSettings().observe(this){ isDarkModeActive : Boolean ->
            if(isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding.switchTheme.isChecked = true
            } else{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding.switchTheme.isChecked = false
            }

        }

        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked:Boolean ->
            settingPreferencesViewModel.saveThemeSetting(isChecked)
        }
    }
}