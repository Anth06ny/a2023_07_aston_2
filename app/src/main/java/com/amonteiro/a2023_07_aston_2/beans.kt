package com.amonteiro.a2023_07_aston_2

import java.util.Random

fun main() {

//    var car = CarBean("Seat", "leon")
//    var car2 = CarBean("Seat", "leon")
//    car.color = "Grise"
//
//    println(car)
//    println(car2)
//    println("C'est une ${car.marque} ${car.model} ${car.color}")
//    println(car.print())
//
//    var house = HouseBean("Rouge", 10,3)
//    house.print()
//
//    PrintRandomIntBean()

//    val plane = PlaneBean("Toto")
//    println("${plane.name} : ${plane.id}")
//    plane.name = "bob"
//    println("${plane.name} : ${plane.id}")
//    //L'id doit avoir changé
//
//    val plane2 = PlaneBean("Toto")
//
//    println(plane)
//    println(plane2)
//
//    var u1 = User
//    u1.name = "toto"
//    println("u1 $u1 : ${u1.name}")
//
//    var u2 = User
//    u2.name = "tata"
//    println("u2 $u2 : ${u2.name}")
//    println("u1 $u1 : ${u1.name}")

    val randomName = RandomName()
    randomName.add("toto")
    repeat(10) {
        println(randomName.next() + " ")
    }

}

/* -------------------------------- */
// API Weather
/* -------------------------------- */

data class WeatherBean(var name: String, var main: TempBean, var wind: WindBean)
data class TempBean(var temp: Double)
data class WindBean(var speed: Double)

/* -------------------------------- */
// EXO
/* -------------------------------- */

class RandomName {
    private val list = arrayListOf("Bob", "Tobby", "bobby")
    private var oldValue = ""

    fun add(name: String) = if (name.isNotBlank() && name !in list) list.add(name) else false

    fun next() = list.random()

    fun nextDiff(): String {
        var value = next()
        while (value == oldValue) {
            value = next()
        }
        oldValue = value
        return value
    }

    fun nextDiffV2() = list.filter { it != oldValue }.random().also {
        oldValue = it

    }

    fun next2() = Pair(nextDiff(), nextDiff())

}

object User {
    var name: String = ""
}

class PlaneBean(name: String) {
    var id = name.hashCode()
        private set

    var name = name
        set(value) {
            field = value
            id = value.hashCode()
        }
}

class PrintRandomIntBean(var max: Int) {
    private val random = Random()

    init {
        println(random.nextInt(max))
        println(random.nextInt(max))
        println(random.nextInt(max))
    }

    constructor() : this(100) {
        println(random.nextInt(max))
    }

}

class HouseBean(var color: String, largeur: Int, longueur: Int) {
    var surface = largeur * longueur

    fun print() = println("La maison $color fait ${surface}m²")
}

data class CarBean(var marque: String, var model: String) {
    var color = ""

    fun print() = "C'est une $marque $model $color"
}