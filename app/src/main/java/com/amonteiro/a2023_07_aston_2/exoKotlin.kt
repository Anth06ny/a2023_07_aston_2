package com.amonteiro.a2023_07_aston_2

fun main() {
////            Créer une variable v1 de type String et y mettre la chaine "toto"
//    var v1 = "toto"
//    println(v1.uppercase())
//
////    Créer une variable v2 de type String nullable et y mettre la chaine "toto"
//    var v2: String? = "toto"
//    println(v2?.uppercase())
//
////    Afficher dans la console sa version en majuscule. Obligé de mettre le safe check
////    Créer une variable v3 de type String nullable et y mettre la valeur null
//    var v3: String? = null
//    println(v3?.uppercase())
//
//    if (v1.isNotBlank()) {
//
//    }
//
//    if (v3.isNullOrBlank()) {
//        println("v3 est null")
//    }
//
//    var res = boulangerie(1)
//    println("res=$res")
//
//    print("Entrez un texte : ")
//    val a = readLine()
//    println("Le texte : $a")

    println(scanNumber("Donnez un nombre : "))


}

fun scanText(question:String): String {
    print(question)
    return readLine() ?: ""
}

fun scanNumber(question:String)= scanText(question).toIntOrNull() ?: -1

fun boulangerie(nbCroissant : Int = 0, nbBaguette : Int = 0, nbSand :Int = 0)
       = nbCroissant * PRICE_CROI + nbBaguette * PRICE_BAG + nbSand * PRICE_SAND


fun min(a: Int, b: Int, c: Int) = if (a < b && a < c) a else if (b < a && b < c) b else c

fun pair(c:Int) = c%2 == 0
fun myPrint(chaine:String) = println("#$chaine#")


