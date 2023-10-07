package com.dicoding.githubuser.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FavoriteUser::class], version = 1)
abstract class FavoriteUserDataBase : RoomDatabase(){
    abstract fun favoriteUserDao() : FavoriteUserDao

    companion object{
        @Volatile
        private var INSTANCE : FavoriteUserDataBase? = null

        @JvmStatic
        fun getDatabase(context: Context):FavoriteUserDataBase{
            if (INSTANCE == null){
                synchronized(FavoriteUserDataBase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FavoriteUserDataBase::class.java, "favorite_user_database")
                        .build()
                }
            }

            return INSTANCE as FavoriteUserDataBase
        }
    }
}