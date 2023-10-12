package com.amonteiro.a2023_07_aston_2

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

//Utilisation


object RequestUtils {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun loadWeather(cityName : String) : WeatherBean {

        Thread.sleep(5000)

        //Controle
        if(cityName.trim().length < 3) {
            throw Exception("Il faut au moins 3 caractères")
        }

        //requete
       var json : String =  sendGet("https://api.openweathermap.org/data/2.5/weather?q=$cityName&appid=b80967f0a6bd10d23e44848547b26550&units=metric&lang=fr")

        //parsing
        return gson.fromJson(json, WeatherBean::class.java)

        //Controle extraction
    }

    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder().url(url).build()
        //Execution de la requête
        return client.newCall(request).execute().use { //it:Response
            //use permet de fermer la réponse qu'il y ait ou non une exception
            //Analyse du code retour
            if (!it.isSuccessful) {
                throw Exception("Réponse du serveur incorrect :${it.code}")
            }
            //Résultat de la requête
            it.body.string()
        }
    }
}

