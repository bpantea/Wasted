package com.wasted.application.view_model

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.wasted.application.backend.AuthService
import com.wasted.application.backend.UserService
import com.wasted.application.model.ExtraFieldsUser
import com.wasted.application.model.User
import com.wasted.application.utils.retrofit.RetrofitProvider
import kotlinx.coroutines.launch
import java.lang.Exception
import java.lang.IllegalStateException
import java.net.SocketException

class UserViewModel(application: Application) : AndroidViewModel(application) {

    private val userService = RetrofitProvider.createService(application, UserService::class.java)
    var fullUser: MutableLiveData<User?> = MutableLiveData(null)

    fun fetchFullUser() {
        val idUser = AuthService.idUser ?: throw IllegalStateException("User not logged in")
        viewModelScope.launch {
            val user = userService.getFullUser(idUser)
            fullUser.value = user
        }
    }

    fun updateUser(user: ExtraFieldsUser) {
        viewModelScope.launch {
            val idUser = AuthService.idUser ?: throw IllegalStateException("User not logged in")
            try {
                userService.addExtraFieldsToUser(idUser, user)
            } catch (e: SocketException) {
                Toast.makeText(getApplication<Application>().applicationContext!!, "SocketException", Toast.LENGTH_LONG).show()
            } catch (e: Exception) {
                Toast.makeText(getApplication<Application>().applicationContext!!, "SocketException", Toast.LENGTH_LONG).show()
            }
        }
    }
}