package com.example.quizzadeira.dao

import com.example.quizzadeira.model.user.User
import com.example.quizzadeira.model.user.UserCallback
import com.example.quizzadeira.model.user.UserInput
import com.example.quizzadeira.model.user.UserLogin
import com.example.quizzadeira.service.ServiceUser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DAOuser {

    val url = "https://super-trivia-server.herokuapp.com/"
    val retrofit = Retrofit.Builder()
            .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(ServiceUser::class.java)

    fun login(userLogin: UserLogin, finished: (User) -> Unit) {
        service.login(userLogin).enqueue(object : Callback<UserCallback> {
            override fun onResponse(call: Call<UserCallback>, response: Response<UserCallback>) {
                if(response.body() != null){

                    if(response.isSuccessful) {
                        val user = response.body()!!
                        finished(user.data.user!!)
                    }
                }
            }

            override fun onFailure(call: Call<UserCallback>, t: Throwable) {}
        })
    }
    fun insert(userInput: UserInput, finished: (User) -> Unit) {
        service.insert(userInput ).enqueue(object : Callback<UserCallback> {
            override fun onResponse(call: Call<UserCallback>, response: Response<UserCallback>) {

                if(!response.isSuccessful){ }
                else{
                    val user = response.body()!!
                    finished(user.data.user!!)
                }
            }
            override fun onFailure(call: Call<UserCallback>, t: Throwable) { }
        })
    }
    fun get(id: Long, finished: (user: User) -> Unit) {
        service.getUser(id).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                val user = response.body()!!
                finished(user)
            }
            override fun onFailure(call: Call<User>, t: Throwable) { }
        })
    }
}