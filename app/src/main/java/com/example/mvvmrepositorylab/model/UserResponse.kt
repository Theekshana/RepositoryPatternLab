package com.example.mvvmrepositorylab.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
    var page: Int,
    @SerializedName("per_page")
    var perPage: Int,
    var total: Int,
    @SerializedName("total_pages")
    var totalPages: Int,
    var data: List<User>,
    var support: Support

)

data class Support(
    var url: String,
    var text: String
)

