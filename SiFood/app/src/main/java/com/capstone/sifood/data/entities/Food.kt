package com.capstone.sifood.data.entities

import androidx.room.Entity


@Entity(tableName = "food")
data class Food(
    var id: Int = 0,
    var name: String? = null,
    var province: String? = null,
    var description: String? = null,
    var imgUrl: String? = null,
    @field:JvmField
    var popular: Boolean? = null
)
