package com.example.quizzadeira.model.categorie

import com.google.gson.annotations.SerializedName

data class Categorie (
    @SerializedName("name") var name: String,
) {
    var id: Long? = null
}