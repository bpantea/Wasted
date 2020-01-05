package com.wasted.application.view_model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wasted.application.backend.DrinkService
import com.wasted.application.model.Drink
import com.wasted.application.utils.retrofit.RetrofitProvider
import kotlinx.coroutines.launch

class DrinkViewModel(application: Application) : AndroidViewModel(application) {

    var currentDrink: MutableLiveData<Drink?> = MutableLiveData(null)
    private val drinkService: DrinkService = RetrofitProvider.createService(application, DrinkService::class.java)

    /**
     * The response will be in currentDrink
     */
    fun startGetDrink(id: String) {
        viewModelScope.launch {
            currentDrink.value = drinkService.getDrink(id)
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
            drinkService.updateDrink(drink)
        }
    }
}