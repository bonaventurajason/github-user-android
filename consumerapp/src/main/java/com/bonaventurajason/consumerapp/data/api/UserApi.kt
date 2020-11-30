package com.bonaventurajason.consumerapp.data.api

import com.bonaventurajason.consumerapp.data.model.SearchResponse
import com.bonaventurajason.consumerapp.data.model.User
import com.bonaventurajason.consumerapp.data.model.UserDetailResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface UserApi {
    @GET("search/users")
    @Headers("Authorization: token 691397676aaee754ea4f60a6c77a1428a7a0ab45")
    suspend fun searchByUsername(
        @Query("q")
        username: String
    ) : Response<SearchResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 691397676aaee754ea4f60a6c77a1428a7a0ab45")
    suspend fun detailByUsername(
        @Path("username") username: String
    ) : Response<UserDetailResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 691397676aaee754ea4f60a6c77a1428a7a0ab45")
    suspend fun followerByUsername(
        @Path("username") username: String
    ) : Response<List<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 691397676aaee754ea4f60a6c77a1428a7a0ab45")
    suspend fun followingByUsername(
        @Path("username") username: String
    ) : Response<List<User>>





}