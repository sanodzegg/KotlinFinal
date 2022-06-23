package com.example.booksapp.data.local.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "book_table")
data class Book(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    val title:String,
    val subtitle:String,
    val isbn13:String,
    val price:String,
    val image:String
)