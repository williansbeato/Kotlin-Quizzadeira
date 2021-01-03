package com.example.quizzadeira.dao

import com.example.quizzadeira.model.ranking.RankingCallBack
import com.example.quizzadeira.model.ranking.RankingUser
import com.example.quizzadeira.service.ServiceRanking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DAORanking {

    val url = "https://super-trivia-server.herokuapp.com/"
    val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(ServiceRanking::class.java)

    fun getAll(finished: (category: List<RankingUser>) -> Unit) {

        service.getAll().enqueue(object : Callback<RankingCallBack> {
            override fun onResponse(call: Call<RankingCallBack>, callBack: Response<RankingCallBack>) {

                if(!callBack.isSuccessful){ }
                else{
                    val rankingUser = callBack.body()!!
                    finished(rankingUser.data.ranking)
                }
            }
            override fun onFailure(call: Call<RankingCallBack>, t: Throwable) { }
        })
    }
}