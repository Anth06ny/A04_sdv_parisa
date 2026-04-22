package com.amonteiro.a04_sdv_parisa.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amonteiro.a04_sdv_parisa.data.remote.KtorWeatherApi
import com.amonteiro.a04_sdv_parisa.data.remote.WeatherEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

suspend fun main() {
    println("start")
    val viewModel = MainViewModel()
    viewModel.loadWeathers("")
    //Affichage de la liste (qui doit être remplie) contenue dans la donnée observable
    println("List : ${viewModel.dataList.value}")

    while (viewModel.runInProgress.value) {
        println("Attente...")
        delay(500)
    }

    println("List : ${viewModel.dataList.value}")
    println("ErrorMessage : ${viewModel.errorMessage.value}" )


    //Pour que le programme s'arrête, inutile sur Android
    KtorWeatherApi.close()
    println("End")
}

class MainViewModel : ViewModel() {
    //MutableStateFlow est une donnée observable
    val dataList = MutableStateFlow(emptyList<WeatherEntity>())
    val runInProgress = MutableStateFlow(false)
    val errorMessage = MutableStateFlow("")


    fun loadWeathers(cityName: String) {

        runInProgress.value = true


        viewModelScope.launch(Dispatchers.IO) {
            try {
                dataList.value = KtorWeatherApi.loadWeathers(cityName)
            }
            catch (e: Exception) {
                e.printStackTrace()
                errorMessage.value = e.message ?: "Une erreur est survenue"
            }
            finally {
                runInProgress.value = false
            }
        }

    }
}