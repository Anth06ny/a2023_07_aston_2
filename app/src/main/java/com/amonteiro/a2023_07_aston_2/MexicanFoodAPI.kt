package com.amonteiro.a2023_07_aston_2

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {
    println(MexicanFoodAPI.loadFoodById(4))
}

object MexicanFoodAPI {

    fun loadFoodById(id:Int = 4): MexicanFoodBean {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://the-mexican-food-db.p.rapidapi.com/$id")
            .get()
            .addHeader("X-RapidAPI-Key", "93329c7cf9msha136bd696cd1040p10a1dejsnbc52cdb0746e")
            .addHeader("X-RapidAPI-Host", "the-mexican-food-db.p.rapidapi.com")
            .build()

        client.newCall(request).execute().use {

            if (!it.isSuccessful) {

                val errorJson = it.body.string()
                if(!errorJson.isNullOrBlank()) {
                    val error = Gson().fromJson(errorJson, ErrorBean::class.java)
                    throw Exception("Erreur : " + error.message)
                }
                throw Exception("Erreur code incorrect : ${it.code}\n ${it.body.string()}")
            }

            val json = it.body.string()
            val tab = Gson().fromJson(json,MexicanFoodBean::class.java)

            return tab
        }
    }
}

data class MexicanFoodBean(
    var description: String,
    var difficulty: String,
    var id: String,
    var image: String,
    var ingredients: List<String>,
    var portion: String,
    var time: String,
    var title: String
)