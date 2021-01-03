package com.example.quizzadeira.dao

import com.example.quizzadeira.model.game.Game
import com.example.quizzadeira.model.game.GameCallBack
import com.example.quizzadeira.model.game.endgame.EndGameCallBack
import com.example.quizzadeira.model.game.endgame.EndGameData
import com.example.quizzadeira.service.ServiceGame
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DAOGame {
    val url = "https://super-trivia-server.herokuapp.com/"
    val retrofit = Retrofit.Builder()
            .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(ServiceGame::class.java)

    fun startGame(token: String, finished: ((Game)) -> Unit) {

        service.startGame(token).enqueue(object : Callback<GameCallBack> {
            override fun onResponse(call: Call<GameCallBack>, response: Response<GameCallBack>) {

                if (!response.isSuccessful) { }
                else {
                    val game = response.body()!!

                    if(game.status == "success"){
                        finished(game.data.game)
                    }
                }
            }

            override fun onFailure(call: Call<GameCallBack>, t: Throwable) { }
        })
    }
    fun startGameWhitSetup(token: String, difficulty:String, category_id: Long?, finished: ((Game)) -> Unit) {

        service.startGameWithSetup(token,difficulty,category_id).enqueue(object : Callback<GameCallBack> {
            override fun onResponse(call: Call<GameCallBack>, response: Response<GameCallBack>) {

                if (!response.isSuccessful) { }
                else {
                    val game = response.body()!!

                    if(game.status == "success"){
                        finished(game.data.game)
                    }
                }
            }

            override fun onFailure(call: Call<GameCallBack>, t: Throwable) { }
        })
    }
    fun endGame(token: String, finished: ((EndGameData)) -> Unit) {

        service.endGame(token).enqueue(object : Callback<EndGameCallBack> {
            override fun onResponse(call: Call<EndGameCallBack>, response: Response<EndGameCallBack>) {

                if (!response.isSuccessful) { }
                else {
                    val end = response.body()!!
                    finished(end.data)
                }
            }
            override fun onFailure(call: Call<EndGameCallBack>, t: Throwable) { }
        })
    }
}