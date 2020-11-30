package com.bonaventurajason.githubuser.data.repository

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bonaventurajason.githubuser.data.api.RetrofitInstance
import com.bonaventurajason.githubuser.data.model.User
import com.bonaventurajason.githubuser.helper.Constant.PROVIDER_USER_CONTENT_URI
import com.bonaventurajason.githubuser.helper.Utils.toContentValues
import com.bonaventurajason.githubuser.helper.Utils.toListUser
import com.bonaventurajason.githubuser.helper.Utils.toUser

class UserRepository {
    suspend fun searchUser(username: String) = RetrofitInstance.api.searchByUsername(username)

    suspend fun getUserDetail(username: String) = RetrofitInstance.api.detailByUsername(username)

    suspend fun getUserFollower(username: String) = RetrofitInstance.api.followerByUsername(username)

    suspend fun getUserFollowing(username: String) = RetrofitInstance.api.followingByUsername(username)

    fun insertFavouriteUser(user: User, context: Context) : LiveData<Long>{
        val insertLiveData = MutableLiveData<Long>()
        val cursor =
            context.contentResolver.insert(PROVIDER_USER_CONTENT_URI.toUri(), user.toContentValues())

        cursor?.let {
            insertLiveData.postValue(1)
        }
        return insertLiveData

    }

    fun getFavouriteUser(context: Context): LiveData<List<User>>{
        val userLiveData = MutableLiveData<List<User>>()

        val cursor =
            context.contentResolver.query(PROVIDER_USER_CONTENT_URI.toUri(), null, null, null, null)
        cursor?.let {
            userLiveData.postValue(it.toListUser())

            cursor.close()
        }
        return userLiveData

    }


    fun isUserFavourite(id: Int, context: Context) : LiveData<User>{
        val userFavouriteLiveData = MutableLiveData<User>()

        val cursor =
            context.contentResolver.query("$PROVIDER_USER_CONTENT_URI/$id".toUri(), null, null, null, null)

        cursor?.let {
            if(cursor.moveToFirst()){
                userFavouriteLiveData.postValue(it.toUser())
            }
            else{
                userFavouriteLiveData.postValue(null)
            }
            cursor.close()
        }
        return userFavouriteLiveData

    }

    fun deleteFavouriteUser(user: User, context: Context) : LiveData<Long>{
        val deleteLiveData = MutableLiveData<Long>()
        val cursor =
            context.contentResolver.delete("$PROVIDER_USER_CONTENT_URI/${user.id}".toUri(), null, null)
        cursor.let { deleteLiveData.postValue(it.toLong()) }

        return deleteLiveData
    }





}