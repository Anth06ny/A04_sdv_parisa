package com.amonteiro.a04_sdv_parisa.exoKotlin

import com.amonteiro.a04_sdv_parisa.data.remote.WindEntity


class MyLiveData<T>(value: T) {

    var value: T = value
        set(newValue) {
            field = newValue
            action(newValue)
        }

    var action: (T) -> Unit = {}
}

fun main() {
    //exo1()

    var i = MyLiveData(WindEntity(0.0))
    i.action = { println(i.value) }

    i.value = WindEntity(5.0)
    i.value = i.value.copy(speed = 6.0)



}

fun exo1() {
    //Déclaration
    val lower: (String) -> Unit = { it: String -> println(it.lowercase()) }
    val lower2 = { it: String -> println(it.lowercase()) }
    val lowe3: (String) -> Unit = { it -> println(it.lowercase()) }
    val lower4: (String) -> Unit = { println(it.lowercase()) }

    //Appel
    lower("Coucou")

    val hour: (Int) -> Int = { it / 60 }
    println(hour(123))

    val max = { a: Int, b: Int -> Math.max(a, b) }

    val reverse: (String) -> String = { it.reversed() }

    var minToMinHour: ((Int?) -> Pair<Int, Int>?)? = { if (it == null) null else Pair(it / 60, it % 60) }

    println(minToMinHour?.invoke(123))
    println(minToMinHour?.invoke(null))
    minToMinHour = null


}