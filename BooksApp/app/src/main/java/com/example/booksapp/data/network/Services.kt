package com.example.booksapp.data.network

import com.example.booksapp.domain.entities.BookDetails
import com.example.booksapp.domain.entities.BooksData
import com.example.booksapp.domain.entities.BooksSearchData
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Services {
    @GET("new")
    suspend fun getFromApi():BooksData

    @GET("search/{query}/{page}")
    suspend fun searchBook(@Path("query")query: String,@Path("page")page:Int): BooksSearchData

    @GET("books/{isbn13}")
    suspend fun booksDetail(@Path("isbn13")isbn13:String): BookDetails
}