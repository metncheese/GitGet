package com.rie.githubuser.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.rie.githubuser.database.FavoriteUserDao
import com.rie.githubuser.database.FavoriteUserRoomDatabase
import com.rie.githubuser.response.ItemsSearch
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository (application: Application) {
    private val mFavoriteUserDao: FavoriteUserDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    init {
        val db = FavoriteUserRoomDatabase.getDatabase(application)
        mFavoriteUserDao = db.favoriteUserDao()
    }
    fun getAllFavoriteUser(): LiveData<List<ItemsSearch>> = mFavoriteUserDao.getAllFavoritUser()
    fun getFavoriteUserByUsername(login: String): LiveData<ItemsSearch> = mFavoriteUserDao.getFavoriteUserByUsername(login)
    fun insert(favoriteuser: ItemsSearch) {
        executorService.execute { mFavoriteUserDao.insert(favoriteuser) }
    }
    fun delete(favoriteuser: ItemsSearch) {
        executorService.execute { mFavoriteUserDao.delete(favoriteuser) }
    }
    fun update(favoriteuser: ItemsSearch) {
        executorService.execute { mFavoriteUserDao.update(favoriteuser) }
    }
}