package com.bonaventurajason.githubuser.ui.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.bonaventurajason.githubuser.GithubUserApplication
import com.bonaventurajason.githubuser.data.model.SearchResponse
import com.bonaventurajason.githubuser.data.model.User
import com.bonaventurajason.githubuser.data.model.UserDetailResponse
import com.bonaventurajason.githubuser.data.repository.UserRepository
import com.bonaventurajason.githubuser.helper.Resource
import kotlinx.coroutines.launch
import retrofit2.Response

class UserViewModel(
    val userRepository: UserRepository,
    app: Application
) : AndroidViewModel(app) {

    val searchUserLiveData: MutableLiveData<Resource<SearchResponse>> = MutableLiveData()
    val detailUserLiveData: MutableLiveData<Resource<UserDetailResponse>> = MutableLiveData()
    val followerUserLiveData: MutableLiveData<Resource<List<User>>> = MutableLiveData()
    val followingUserLiveData: MutableLiveData<Resource<List<User>>> = MutableLiveData()


    fun searchUser(username: String) = viewModelScope.launch {
        safeSearchUserCall(username)
    }

    fun getUserDetail(username: String) = viewModelScope.launch {
        safeSearchGetUserDetailCall(username)
    }

    fun getUserFollower(username: String) = viewModelScope.launch {
        safeSearchGetUserFollowerCall(username)
    }
    fun getUserFollowing(username: String) = viewModelScope.launch {
        safeSearchGetUserFollowingCall(username)
    }

    fun saveFavouriteUser(user: User, context: Context) =
        userRepository.insertFavouriteUser(user, context)

    fun getFavouriteUser(context: Context) =
        userRepository.getFavouriteUser(context)

    fun checkFavouriteUser(id: Int, context: Context) =
        userRepository.isUserFavourite(id, context)

    fun deleteFavouriteUser(user: User, context: Context) =
        userRepository.deleteFavouriteUser(user, context)


    private suspend fun safeSearchUserCall(username: String) {
        searchUserLiveData.postValue(Resource.Loading())
        try {
            if (hasInternetConnection()) {
                val response = userRepository.searchUser(username)
                searchUserLiveData.postValue(handleSearchUserResponse(response))
            } else {
                searchUserLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t : Throwable){
            searchUserLiveData.postValue(Resource.Error("An error has occured ${t.message}"))
        }
    }
    private suspend fun safeSearchGetUserDetailCall(username: String) {
        detailUserLiveData.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()){
                val response = userRepository.getUserDetail(username)
                detailUserLiveData.postValue(handleGetUserDetailResponse(response))
            }
            else{
                detailUserLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable){
            detailUserLiveData.postValue(Resource.Error("An error has occured ${t.message}"))
        }
    }

    private suspend fun safeSearchGetUserFollowerCall(username: String) {
        followerUserLiveData.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()){
                val response = userRepository.getUserFollower(username)
                followerUserLiveData.postValue(handleGetUserFollowerResponse(response))
            }
            else{
                followerUserLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable){
            followerUserLiveData.postValue(Resource.Error("An error has occured ${t.message}"))
        }
    }
    private suspend fun safeSearchGetUserFollowingCall(username: String) {
        followingUserLiveData.postValue(Resource.Loading())
        try {
            if(hasInternetConnection()){
                val response = userRepository.getUserFollowing(username)
                followingUserLiveData.postValue(handleGetUserFollowerResponse(response))
            }
            else{
                followingUserLiveData.postValue(Resource.Error("No internet connection"))
            }
        } catch (t: Throwable){
            followingUserLiveData.postValue(Resource.Error("An error has occured ${t.message}"))
        }
    }
    private fun handleSearchUserResponse(response: Response<SearchResponse>): Resource<SearchResponse>? {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleGetUserDetailResponse(response: Response<UserDetailResponse>): Resource<UserDetailResponse>? {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleGetUserFollowerResponse(response: Response<List<User>>): Resource<List<User>>? {
        if(response.isSuccessful){
            response.body()?.let { resultResponse ->
                return Resource.Success(resultResponse)
            }
        }
        return Resource.Error(response.message())
    }


    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<GithubUserApplication>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork = connectivityManager.activeNetwork ?: return false
            val capabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
            return when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                else -> false
            }
        } else {
            connectivityManager.activeNetworkInfo?.run {
                return when (type) {
                    ConnectivityManager.TYPE_WIFI -> true
                    ConnectivityManager.TYPE_MOBILE -> true
                    ConnectivityManager.TYPE_ETHERNET -> true
                    else -> false
                }
            }
        }
        return false
    }
}