package com.bonaventurajason.githubuser.ui.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bonaventurajason.githubuser.data.repository.UserRepository

class UserViewModelProviderFactory(
    val app: Application,
    val userRepository: UserRepository
) : ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return UserViewModel(userRepository, app) as T
    }

}