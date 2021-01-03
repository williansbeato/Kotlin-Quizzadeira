package com.example.quizzadeira.service

import com.example.quizzadeira.model.categorie.CategorieCallBack
import retrofit2.Call
import retrofit2.http.GET

interface ServiceCategorie {
    @GET("/categories")
    fun getAll(): Call<CategorieCallBack>
}