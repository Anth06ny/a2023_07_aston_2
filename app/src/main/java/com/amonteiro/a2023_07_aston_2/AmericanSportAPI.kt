package com.amonteiro.a2023_07_aston_2

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {
    val tab = AmericanSportAPI.loadAllSports()


    //tab.filter { it.group == "American Football" }.forEach { println(it) }
    tab.map { it.description }.sortedBy { it }.forEach { println(it) }
}

object AmericanSportAPI {

    fun loadAllSports(all: Boolean = false): List<SportBean> {
        val client = OkHttpClient()

        val request = Request.Builder()
            .url("https://odds.p.rapidapi.com/v4/sports?all=dzijd$all")
            .get()
            .addHeader("X-RapidAPI-Key", "93329c7cf9msha136bd696cd1040p10a1dejsnbc52cdb0746e")
            .addHeader("X-RapidAPI-Host", "odds.p.rapidapi.com")
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
            val tab = Gson().fromJson(json, Array<SportBean>::class.java)

            return tab.toList()
        }
    }

}

/* -------------------------------- */
// bean
/* -------------------------------- */
data class SportBean(var group: String, var description: String? = null)
data class ErrorBean(var message: String?)