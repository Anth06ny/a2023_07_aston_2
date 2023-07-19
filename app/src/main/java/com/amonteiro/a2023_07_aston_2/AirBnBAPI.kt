package com.amonteiro.a2023_07_aston_2

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {
    //Lazy loading + une seule instanciation
//    var html = RequestUtils.sendGet("https://www.google.fr")
//    println("html=$html")

    val appart = AirBnBAPI.loadAppart(scanText("Donnez une ville : "))
    println(appart)
}

object AirBnBAPI {

    private val client = OkHttpClient()
    private val gson = Gson()

    fun loadAppart(cityName : String) : AirBnBAPIResultBean {

        //Controle
        if(cityName.trim().length < 3) {
            throw Exception("Il faut au moins 3 caractères")
        }

        //requete
        var json : String =  sendGet("https://airbnb-search.p.rapidapi.com/property/search?query=$cityName&locale=en-US&currency=USD")

        //parsing
        return gson.fromJson(json, AirBnBAPIResultBean::class.java)

        //Controle extraction
    }

    //Méthode qui prend en entrée une url, execute la requête
    //Retourne le code HTML/JSON reçu
    fun sendGet(url: String): String {
        println("url : $url")
        //Création de la requête
        val request = Request.Builder()
            .url(url)
            .get()
            .addHeader("X-RapidAPI-Key", "93329c7cf9msha136bd696cd1040p10a1dejsnbc52cdb0746e")
            .addHeader("X-RapidAPI-Host", "airbnb-search.p.rapidapi.com")
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

/* -------------------------------- */
// Beans
/* -------------------------------- */
data class AirBnBAPIResultBean(
    var data : Data,
)

data class Data(
    var homes: List<Home>,
)

data class Home(
    var titleLabel: TitleLabel,
)

data class TitleLabel(
    var __typename: String,
    var textElement: TextElementX,
)

data class TextElementX(
    var __typename: String,
    var text: String
)
