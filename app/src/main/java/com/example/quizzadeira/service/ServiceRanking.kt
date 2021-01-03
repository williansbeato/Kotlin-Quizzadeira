package com.example.quizzadeira.service

import com.example.quizzadeira.model.ranking.RankingCallBack
import retrofit2.Call
import retrofit2.http.GET

interface ServiceRanking {
    @GET("/ranking")
    fun getAll():Call<RankingCallBack>
}