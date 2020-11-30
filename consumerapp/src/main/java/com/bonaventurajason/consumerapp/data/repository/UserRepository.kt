package com.bonaventurajason.consumerapp.data.repository

import android.content.Context
import androidx.core.net.toUri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bonaventurajason.consumerapp.data.model.User
import com.bonaventurajason.consumerapp.helper.Constant.PROVIDER_USER_CONTENT_URI
import com.bonaventurajason.consumerapp.helper.Utils.toListUser


class UserRepository {
    fun getFavouriteUser(context: Context): LiveData<List<User>> {
        val userLiveData = MutableLiveData<List<User>>()

        val cursor =
            context.contentResolver.query(PROVIDER_USER_CONTENT_URI.toUri(), null, null, null, null)
        cursor?.let {
            userLiveData.postValue(it.toListUser())

            cursor.close()
        }
        return userLiveData

    }

}