package com.amonteiro.a2023_07_aston_2

import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody

fun main() {
    //Lazy loading + une seule instanciation
//    var html = RequestUtils.sendGet("https://www.google.fr")
//    println("html=$html")

   TranslateAPI.loadAppart()
}

object TranslateAPI {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun loadAppart()  {
        //requete
        var json : String =  sendGet()
        println(json)

    }

    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(): String {
        //println("url : $url")
        //Création de la requête
        val mediaType = "application/x-www-form-urlencoded".toMediaTypeOrNull()
        val body = RequestBody.create(mediaType, "q=English%20is%20hard%2C%20but%20detectably%20so")
        val request = Request.Builder()
            .url("https://google-translate1.p.rapidapi.com/language/translate/v2/detect")
            .post(body)
            .addHeader("content-type", "application/x-www-form-urlencoded")
            .addHeader("Accept-Encoding", "application/gzip")
            .addHeader("X-RapidAPI-Key", "93329c7cf9msha136bd696cd1040p10a1dejsnbc52cdb0746e")
            .addHeader("X-RapidAPI-Host", "google-translate1.p.rapidapi.com")
            .build()

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
