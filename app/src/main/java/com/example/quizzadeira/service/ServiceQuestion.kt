package com.example.quizzadeira.service

import com.example.quizzadeira.model.question.QuestionCallBack
import com.example.quizzadeira.model.question.verify.VerifyCallBack
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ServiceQuestion {
    @GET("/problems/next")
    fun nextQuestion(@Header("Authorization") token : String): Call<QuestionCallBack>

    @GET("/problems/view")
    fun existQuestion(@Header("Authorization") token : String): Call<QuestionCallBack>

    @POST("/problems/answer?")
    fun answerQuestion(@Header("Authorization") token: String, @Query("answer") answer: Int): Call<VerifyCallBack>
}