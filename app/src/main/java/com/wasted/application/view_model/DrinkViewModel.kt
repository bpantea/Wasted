package com.wasted.application.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wasted.application.backend.DrinkService
import com.wasted.application.model.Drink
import com.wasted.application.utils.retrofit.RetrofitProvider
import kotlinx.coroutines.launch
import java.lang.Exception

class DrinkViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var scannerBarcode: String? = null
    }

    var currentDrink: MutableLiveData<Drink?> = MutableLiveData(null)
    private val drinkService: DrinkService = RetrofitProvider.createService(application, DrinkService::class.java)

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
            drinkService.addDrink(drink)
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