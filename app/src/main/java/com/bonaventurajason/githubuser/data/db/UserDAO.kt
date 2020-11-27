package com.bonaventurajason.githubuser.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.bonaventurajason.githubuser.data.model.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: User) : Long

    @Query("SELECT * FROM user")
    fun getAllUsers(): LiveData<List<User>>

    @Delete
    suspend fun deleteUser(user: User)

    @Query("SELECT * FROM user WHERE login = :username")
    fun checkFavouriteUser(username: String) : LiveData<User>
}