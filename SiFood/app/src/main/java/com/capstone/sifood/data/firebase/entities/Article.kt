package com.capstone.sifood.data.firebase.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int = 0,
    @ColumnInfo(name = "year")
    val year : String,
    @ColumnInfo(name = "title")
    val title : String,
    @ColumnInfo(name = "url")
    val url : String,
    @ColumnInfo(name = "picture")
    val picture : String,
)
