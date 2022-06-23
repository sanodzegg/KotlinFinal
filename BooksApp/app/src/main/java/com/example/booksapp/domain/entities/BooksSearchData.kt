package com.example.booksapp.domain.entities

data class BooksSearchData(
    val total:String,
    val page:String,
    val books:List<SearchBooks>
)
data class SearchBooks(
    val title:String,
    val subtitle:String,
    val isbn13:String,
    val price:String,
    val image:String,
    val url:String
)