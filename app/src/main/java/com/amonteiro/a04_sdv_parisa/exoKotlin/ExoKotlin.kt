package com.amonteiro.a04_sdv_parisa.exoKotlin

import kotlin.random.Random


val v6 = "toto" //requete

fun main() {

    val car = CarEntity("seat","")
    car.color
    car.print()

    val car2 = CarEntity("sdqg","")
    car2.print()

    println("HellowWorld")



    var v1 = "coucou"
    var v2 : String? = "coucou"
    var v3 : String? = null

    println(v1.uppercase())
    println(v2?.uppercase())
    println(v3?.uppercase())


    var v4 : Int? = null
//Laisser le curseur de la souris sur Random pour qu'il vous propose de l'importer
//Choisir celui de Koltin
    if(Random.nextBoolean()){
        v4 = Random.nextInt(10)
    }
    println(v4 ?: "Pas de valeur")

    if(myIsNullOrBlank(v3)) {

    }

    val res = boulangerie(1,2,3)
    println("res=$res")
    boulangerie(nbSand =  5, nbCroi = 2)

}

fun boulangerie(nbCroi:Int = 0, nbBag:Int =0, nbSand:Int =0): Double
   = nbCroi * PRICE_CROISSANT + nbBag * PRICE_BAGUETTE + nbSand * PRICE_SANDWITCH


fun String?.myIsNullOrBlank2(): Boolean {
    return this == null || this.isBlank()
}

fun myIsNullOrBlank(text : String?): Boolean {
    return text == null || text.isBlank()
}

