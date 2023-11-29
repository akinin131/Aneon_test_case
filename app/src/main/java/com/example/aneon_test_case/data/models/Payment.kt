package com.example.aneon_test_case.data.models

import com.google.gson.annotations.SerializedName

data class Payment(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String,
    @SerializedName("amount") val amount: String?,
    @SerializedName("created") val created: Long
)