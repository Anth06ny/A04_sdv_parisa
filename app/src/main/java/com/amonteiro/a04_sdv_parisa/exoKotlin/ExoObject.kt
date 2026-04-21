package com.amonteiro.a04_sdv_parisa.exoKotlin

import java.util.Random

fun main() {




    var car = CarEntity("Seat", "Leon")
    var car2 = CarEntity("Seat", "Leon")
    var car3 = car
    var car4 = car.copy(model = "Ibiza")

    car.color = "grise"
    println(car === car2)
    println(car2)
    println(car3)

    println(
        """
        C'est une ${car.marque} ${car.model} de couleur ${car.color}
    """.trimIndent()
    )

    val house = HouseEntity("bleu", 3, 5)
    house.print()

    val p = PrintRandomIntEntity(40)

    var t1 = ThermometerEntity(min = -20, max = 50, value = 100)
    t1.value = 100
    println(t1.value)

    val ter = ThermometerEntity.getCelsiusThermometer()
}

class RandomName {
    private val list = arrayListOf("toto", "bob", "titi")
    private var oldValue = ""

    fun add(name: String?) = if (!name.isNullOrBlank() && name !in list)
        list.add(name)
    else false

    fun next() = list.random()

    fun addAll(vararg names : String) {
        for(name in names) {
            add(name)
        }
    }

    fun temp(){
    }


    fun nextDiff(): String {
        var newValue = next()
        while(newValue == oldValue) {
            newValue = next()
        }

        oldValue = newValue
        return oldValue
    }

    fun nextDiff2(): String {
        oldValue = list.filter { it != oldValue  }.random()
        return oldValue
    }

    fun nextDiff3() = list.filter { it != oldValue  }.random().also { oldValue = it }

    fun next2() = Pair(nextDiff(), nextDiff())



}

class ThermometerEntity(val min: Int, val max: Int, value: Int) {
    var value: Int = value.coerceIn(min, max)
        set(newValue) {
            field = if (newValue > max) max else if (newValue < min) min else newValue
        }

    companion object {
        fun getCelsiusThermometer() = ThermometerEntity(-30, 50, 0)
    }
}

class PrintRandomIntEntity(val max: Int) {
    private val random: Random = Random()


    init {
        println(random.nextInt(max))
        println(random.nextInt(max))
        println(random.nextInt(max))
    }
}

class HouseEntity(var color: String, width: Int, length: Int) {
    var area = width * length

    fun print() = println("La maison $color fait ${area}m²")
}

data class CarEntity(var marque: String, var model: String) {
    var color = ""

    fun print() = println("$marque - " + model)

}