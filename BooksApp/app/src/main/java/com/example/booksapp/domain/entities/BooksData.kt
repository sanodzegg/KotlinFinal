package com.example.booksapp.domain.entities

data class BooksData(
    val total:String,
    val books:List<Books>
)
data class Books(
    val title:String,
    val subtitle:String,
    val isbn13:String,
    val price:String,
    val image:String,
    val url:String
)