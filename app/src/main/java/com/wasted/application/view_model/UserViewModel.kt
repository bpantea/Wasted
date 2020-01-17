package com.wasted.application.view_model

import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
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
import java.io.IOException
import java.net.HttpURLConnection
import java.net.SocketException
import java.net.URL


class UserViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        var src: String? = null
    }

    private val userService = RetrofitProvider.createService(application, UserService::class.java)
    var fullUser: MutableLiveData<User?> = MutableLiveData(null)

    fun fetchFullUser() {
        val idUser = AuthService.idUser ?: throw IllegalStateException("User not logged in")
        viewModelScope.launch {
            val user = userService.getFullUser(idUser)
            fullUser.value = user
        }
    }

    val bmpObservable = MutableLiveData<Bitmap>()

    fun updateUser(user: ExtraFieldsUser) {
        viewModelScope.launch {
            val idUser = AuthService.idUser ?: throw IllegalStateException("User not logged in")
            try {
                userService.addExtraFieldsToUser(idUser, user)
            } catch (e: SocketException) {
                Toast.makeText(
                    getApplication<Application>().applicationContext!!,
                    "SocketException",
                    Toast.LENGTH_LONG
                ).show()
            } catch (e: Exception) {
                Toast.makeText(
                    getApplication<Application>().applicationContext!!,
                    "SocketException",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun fetchProfilePicture() {
        viewModelScope.launch {
            val obj = object : MyAsync() {

                override fun onPostExecute(bmp: Bitmap) {
                    bmpObservable.value = bmp
                }
            }

            obj.execute()
        }
    }
}

open class MyAsync : AsyncTask<Void, Void, Bitmap>() {

    override fun doInBackground(vararg params: Void): Bitmap? {
        return try {
            val url = URL(UserViewModel.src)
            val connection = url.openConnection() as HttpURLConnection
            connection.doInput = true
            connection.connect()
            val input = connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}