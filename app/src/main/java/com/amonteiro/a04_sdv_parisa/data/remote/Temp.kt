package com.amonteiro.a04_sdv_parisa.data.remote

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    // 1) CoroutineScope custom avec un Job + un Dispatcher
    val scope = CoroutineScope(Job() + Dispatchers.Default)

    // 2) launch : "fire & forget", ne renvoie pas de résultat
    val job1 = scope.launch {
        println("Tâche 1 démarrée sur ${Thread.currentThread().name}")
        delay(1000)
        println("Tâche 1 terminée")
    }

    val job2 = scope.launch {
        println("Tâche 2 démarrée sur ${Thread.currentThread().name}")
        delay(500)
        println("Tâche 2 terminée")
    }

    // On attend les deux coroutines avant de continuer
    job1.join()
    job2.join()

    // 3) async / await : utilisé quand on veut un résultat
    val resultat = scope.async {
        delay(300)
        calculLong(10, 20)
    }
    println("Résultat async = ${resultat.await()}")

    // 4) Lancer plusieurs appels en parallèle et agréger le résultat
    val valeurs = listOf(1, 2, 3, 4, 5).map { n ->
        scope.async {
            delay(200)
            n * n
        }
    }.awaitAll()
    println("Carrés = $valeurs")

    // 5) Annulation propre : un cancel sur le scope annule toutes ses coroutines
    val longJob = scope.launch {
        repeat(10) { i ->
            println("Boucle $i...")
            delay(300)
        }
    }
    delay(700)
    longJob.cancel()
    println("Tâche longue annulée")

    // 6) Toujours libérer le scope quand on n'en a plus besoin
    scope.cancel()
}

private suspend fun calculLong(a: Int, b: Int): Int {
    delay(500)
    return a + b
}
