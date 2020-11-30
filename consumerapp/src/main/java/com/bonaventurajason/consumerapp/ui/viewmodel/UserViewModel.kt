package com.bonaventurajason.consumerapp.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import com.bonaventurajason.consumerapp.data.repository.UserRepository

class UserViewModel(
    val userRepository: UserRepository,
    app: Application
) : AndroidViewModel(app) {


    fun getFavouriteUser(context: Context) = userRepository.getFavouriteUser(context)
}