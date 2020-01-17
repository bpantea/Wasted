package com.wasted.application.view_model

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.wasted.application.backend.AuthService
import com.wasted.application.model.User
import com.wasted.application.utils.WastedApplication
import com.wasted.application.utils.retrofit.RetrofitProvider

class UserViewModel(application: WastedApplication) : AndroidViewModel(application) {

    private val authService = RetrofitProvider.createService(application, AuthService::class.java)

    companion object {
        var fullUser: MutableLiveData<User?> = MutableLiveData(null)
    }

    fun login(account: GoogleSignInAccount) {

    }
}