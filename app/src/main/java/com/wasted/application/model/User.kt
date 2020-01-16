package com.wasted.application.model

import java.util.*

data class User(var id: String,
                var displayName: String,
                var email: String?,
                var profilePicture: String?,
                var birthday: Date?,
                var weight: Double?,
                var gender: Gender?)
