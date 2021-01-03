package com.example.quizzadeira.service

import com.example.quizzadeira.model.game.GameCallBack
import com.example.quizzadeira.model.game.endgame.EndGameCallBack
import retrofit2.Call
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ServiceGame {
    @GET("/games")
    fun startGame(@Header("Authorization") token : String): Call<GameCallBack>

    @GET("/games?")
    fun startGameWithSetup(@Header("Authorization") token: String,
                           @Query("difficulty") difficulty: String,
                           @Query("category_id") category_id: Long?
    ): Call<GameCallBack>

    @DELETE("/games")
    fun endGame(@Header("Authorization") token : String): Call<EndGameCallBack>
}