package com.example.quizzadeira.dao

import com.example.quizzadeira.model.question.QuestionCallBack
import com.example.quizzadeira.model.question.QuestionData
import com.example.quizzadeira.model.question.verify.VerifyCallBack
import com.example.quizzadeira.model.question.verify.VerifyData
import com.example.quizzadeira.service.ServiceQuestion
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DAOQuestion {
    val url = "https://super-trivia-server.herokuapp.com/"
    val retrofit = Retrofit.Builder()
            .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(ServiceQuestion::class.java)

    fun nextQuestion(token: String, finished: (QuestionData) -> Unit) {

        service.nextQuestion(token).enqueue(object : Callback<QuestionCallBack> {
            override fun onResponse(
                    call: Call<QuestionCallBack>,
                    response: Response<QuestionCallBack>
            ) {

                if (!response.isSuccessful) { }
                else {
                    val question = response.body()!!
                    finished(question.data!!)
                }
            }
            override fun onFailure(call: Call<QuestionCallBack>, t: Throwable) {}
        })
    }

    fun existQuestion(token: String, finished: (QuestionData) -> Unit) {

        service.existQuestion(token).enqueue(object : Callback<QuestionCallBack> {
            override fun onResponse(
                    call: Call<QuestionCallBack>,
                    response: Response<QuestionCallBack>
            ) {

                if (!response.isSuccessful) { }
                else {
                    val question = response.body()!!
                    finished(question.data!!)
                }
            }
            override fun onFailure(call: Call<QuestionCallBack>, t: Throwable) {
            }
        })
    }
    fun answerQuestion(answer: Int,token: String, finished: (VerifyData) -> Unit) {

        service.answerQuestion(token,answer).enqueue(object : Callback<VerifyCallBack> {
            override fun onResponse(
                    call: Call<VerifyCallBack>,
                    response: Response<VerifyCallBack>
            ) {

                if (!response.isSuccessful) { }
                else {
                    val verify = response.body()!!
                    finished(verify.data!!)
                }
            }
            override fun onFailure(call: Call<VerifyCallBack>, t: Throwable) { }
        })
    }
}