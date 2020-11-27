package com.bonaventurajason.githubuser.data.repository

import com.bonaventurajason.githubuser.data.api.RetrofitInstance
import com.bonaventurajason.githubuser.data.db.UserDatabase
import com.bonaventurajason.githubuser.data.model.User

class UserRepository(
    val db: UserDatabase
) {
    suspend fun searchUser(username: String) = RetrofitInstance.api.searchByUsername(username)

    suspend fun getUserDetail(username: String) = RetrofitInstance.api.detailByUsername(username)

    suspend fun getUserFollower(username: String) = RetrofitInstance.api.followerByUsername(username)

    suspend fun getUserFollowing(username: String) = RetrofitInstance.api.followingByUsername(username)

    suspend fun insertFavouriteUser(user: User) = db.getUserDao().insertUser(user)

    fun getFavouriteUser() = db.getUserDao().getAllUsers()

    fun isUserFavourite(username: String) = db.getUserDao().checkFavouriteUser(username)

    suspend fun deleteFavouriteUser(user: User) = db.getUserDao().deleteUser(user)





}