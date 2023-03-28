package com.example.mvvmrepositorylab.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var email: String,
    @SerializedName("first_name")
    @ColumnInfo(name = "first_name")
    var firstName: String,
    @ColumnInfo(name = "last_name")
    @SerializedName("last_name")
    var lastName: String,
    var avatar: String,

    )
