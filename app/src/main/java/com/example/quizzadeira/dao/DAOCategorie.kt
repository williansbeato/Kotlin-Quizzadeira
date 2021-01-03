package com.example.quizzadeira.dao

import com.example.quizzadeira.model.categorie.Categorie
import com.example.quizzadeira.model.categorie.CategorieCallBack
import com.example.quizzadeira.service.ServiceCategorie
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DAOCategorie {
    val url = "https://super-trivia-server.herokuapp.com/"
    val retrofit = Retrofit.Builder()
            .baseUrl(url).addConverterFactory(GsonConverterFactory.create())
            .build()

    val service = retrofit.create(ServiceCategorie::class.java)

    fun getAll(finished: (categorie: List<Categorie>) -> Unit) {

        service.getAll().enqueue(object : Callback<CategorieCallBack> {
            override fun onResponse(call: Call<CategorieCallBack>, callBack: Response<CategorieCallBack>) {

                if(!callBack.isSuccessful){ }
                else{
                    val categories = callBack.body()!!
                    categories.data?.let { finished(it.categories) }
                }
            }
            override fun onFailure(call: Call<CategorieCallBack>, t: Throwable) { }
        })
    }
}