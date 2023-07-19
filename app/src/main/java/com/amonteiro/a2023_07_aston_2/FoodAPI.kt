package com.amonteiro.a2023_07_aston_2

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request

fun main() {
    //Lazy loading + une seule instanciation
//    var html = RequestUtils.sendGet("https://www.google.fr")
//    println("html=$html")

    val food = FoodAPI.loadFirstFood()
    println("${food?.food?.label ?: "-"} :  ${food?.food?.image ?: "-"}")
}

object FoodAPI {

    private val client = OkHttpClient()
    private val gson = Gson()

    //Base URL
    private const val URLAPI = "https://edamam-food-and-grocery-database.p.rapidapi.com/api/food-database/v2"


    fun loadFirstFood() : Hint? {
        //requete
        var json : String =  sendGet(URLAPI + "/parser?nutrition-type=cooking&category%5B0%5D=generic-foods&health%5B0%5D=alcohol-free")



        //parsing
        val res =  gson.fromJson(json, FoodResultBean::class.java)

        return res.hints.getOrNull(0)
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
            .addHeader("X-RapidAPI-Host", "edamam-food-and-grocery-database.p.rapidapi.com")
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
data class FoodResultBean(
    var hints: List<Hint>,
)

data class Hint(
    var food: Food,
    var measures: List<Measure>
)

data class Food(
    var category: String,
    var categoryLabel: String,
    var foodId: String,
    var image: String,
    var knownAs: String,
    var label: String,
    var nutrients: Nutrients,
    var uri: String
)

data class Measure(
    var label: String,
    var qualified: List<Qualified>,
    var uri: String,
    var weight: Double
)

data class Nutrients(
    var CHOCDF: Double,
    var ENERC_KCAL: Int,
    var FAT: Double,
    var FIBTG: Double,
    var PROCNT: Double
)

data class Qualified(
    var qualifiers: List<Qualifier>,
    var weight: Int
)

data class Qualifier(
    var label: String,
    var uri: String
)