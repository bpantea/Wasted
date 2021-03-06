package com.wasted.application.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wasted.application.backend.DrinkService
import com.wasted.application.model.Drink
import com.wasted.application.utils.retrofit.RetrofitProvider
import kotlinx.coroutines.launch

class DrinkViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var scannerBarcode: String? = null
    }

    var currentDrink: MutableLiveData<Drink?> = MutableLiveData()
    private val drinkService: DrinkService =
        RetrofitProvider.createService(application, DrinkService::class.java)

    /**
     * The response will be in currentDrink
     */
    fun startGetDrink(id: String) {
        viewModelScope.launch {
            try {
                val drink = drinkService.getDrink(id)
                currentDrink.value = drink
            } catch (e: Exception) {
                currentDrink.value = null
            }

        }
    }

    fun addDrink(drink: Drink) {
        viewModelScope.launch {
            try {
                drinkService.addDrink(drink)
                Toast.makeText(
                    getApplication<Application>().applicationContext!!,
                    "SUCCESS",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    getApplication<Application>().applicationContext!!,
                    "NOT SUCCEED. Maybe you inserted wrong values?",
                    Toast.LENGTH_LONG
                ).show()

            }
        }
    }

    fun deleteDrink(id: String) {
        viewModelScope.launch {
            drinkService.deleteDrink(id)
        }
    }

    fun updateDrink(drink: Drink) {
        viewModelScope.launch {
            drinkService.addDrink(drink)
        }
    }
}