package com.amonteiro.a2023_07_aston_2

fun main() {
    //Lazy loading + une seule instanciation
//    var html = RequestUtils.sendGet("https://www.google.fr")
//    println("html=$html")

    val weather = RequestUtils.loadWeather(scanText("Donnez une ville : "))
    println("Il fait ${weather.main.temp}° à ${weather.name} avec un vent de ${weather.wind.speed} m/s")
}