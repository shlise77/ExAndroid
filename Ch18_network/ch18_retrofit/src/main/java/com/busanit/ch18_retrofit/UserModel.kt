package com.busanit.ch18_retrofit

import com.google.gson.annotations.SerializedName

data class UserModel(
    var id: Int,
    var first_name: String,
    var last_name: String,
    var avatar: String,
    var email: String
)

data class ResponseData(
    @SerializedName("data")
    var userModel: UserModel
)
