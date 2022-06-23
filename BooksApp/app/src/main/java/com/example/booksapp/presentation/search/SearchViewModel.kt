package com.example.booksapp.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.booksapp.data.network.RetrofitInstance
import com.example.booksapp.data.network.Services
import com.example.booksapp.domain.entities.BooksSearchData
import com.example.booksapp.data.handler.Result
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {
    lateinit var searchBooksDataLis : MutableLiveData<BooksSearchData>

    init {
        searchBooksDataLis = MutableLiveData()
    }
    fun getRecyclerListInstance():MutableLiveData<BooksSearchData>{
        return searchBooksDataLis
    }
    fun searchBooks(query:String,page:Int){
        viewModelScope.launch(Dispatchers.IO) {
            val retrofitInstance =  RetrofitInstance.getRetrofitInstance().create(Services::class.java)
            val response = retrofitInstance.searchBook(query,page)
            searchBooksDataLis.postValue(response)
        }
    }


    private val searchedBook = MutableLiveData<Result<BooksSearchData>>()
    val getSearchedBook: LiveData<Result<BooksSearchData>> get() = searchedBook
    fun searchBook(query:String,page:Int){
        viewModelScope.launch(
            CoroutineExceptionHandler { _, exeption ->
                searchedBook.value = Result.Error(exeption)
            }
        ) {
            val retrofitInstance =  RetrofitInstance.getRetrofitInstance().create(Services::class.java)
            val response = retrofitInstance.searchBook(query,page)
            searchedBook.value = Result.Success(response)
        }
    }

}