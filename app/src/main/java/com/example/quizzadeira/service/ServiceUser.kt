package com.example.quizzadeira.service

import com.example.quizzadeira.model.user.User
import com.example.quizzadeira.model.user.UserCallback
import com.example.quizzadeira.model.user.UserInput
import com.example.quizzadeira.model.user.UserLogin
import retrofit2.Call
import retrofit2.http.*

interface ServiceUser {
    @GET("/users/{id}")
    fun getUser(@Path("id") id: Long): Call<User>

    @POST("/users")
    @Headers("Content-Type: application/json")
    fun insert(@Body userInput: UserInput): Call<UserCallback>
    
    @POST("auth/")
    @Headers("Content-Type: application/json")
    fun login(@Body userInput: UserLogin) : Call<UserCallback>
}