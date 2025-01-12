package com.rie.githubuser.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.rie.githubuser.response.ItemsSearch

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favoriteUser: ItemsSearch )
    @Update
    fun update(favoriteUser: ItemsSearch)
    @Delete
    fun delete(favoriteUser: ItemsSearch)
    @Query("SELECT * from ItemsSearch ORDER BY login ASC")
    fun getAllFavoritUser(): LiveData<List<ItemsSearch>>
    @Query("SELECT * FROM ItemsSearch WHERE login = :login")
    fun getFavoriteUserByUsername(login: String): LiveData<ItemsSearch>
}