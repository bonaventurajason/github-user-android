package com.bonaventurajason.githubuser.data.db

import android.database.Cursor
import androidx.lifecycle.LiveData
import androidx.room.*
import com.bonaventurajason.githubuser.data.model.User

@Dao
interface UserDAO {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User) : Long

    @Query("SELECT * FROM user")
    fun getAllUsers(): Cursor

    @Query("SELECT * FROM user WHERE ID = :id")
    fun getUserById(id: Int): Cursor

    @Query("DELETE FROM user WHERE ID = :id")
    fun deleteUser(id: Int): Int

    @Query("SELECT * FROM user WHERE login = :username")
    fun checkFavouriteUser(username: String) : LiveData<User>
}