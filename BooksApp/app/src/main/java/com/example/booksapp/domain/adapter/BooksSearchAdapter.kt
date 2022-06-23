package com.example.booksapp.domain.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.NavArgsLazy
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksapp.R
import com.example.booksapp.databinding.SearchFragmentBinding
import com.example.booksapp.domain.entities.BooksData
import com.example.booksapp.domain.entities.BooksSearchData
import com.example.booksapp.presentation.search.SearchFragment
import com.example.booksapp.presentation.search.SearchFragmentDirections
import com.google.android.material.textview.MaterialTextView

class BooksSearchAdapter(val data: BooksSearchData, val host: SearchFragment) :
    RecyclerView.Adapter<BooksSearchAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val book_img = itemView.findViewById<ImageView>(R.id.book_img)
        val title_of_book = itemView.findViewById<MaterialTextView>(R.id.title_of_book)
        val subtitle_of_book = itemView.findViewById<MaterialTextView>(R.id.subtitle_of_book)
        val price_of_book = itemView.findViewById<MaterialTextView>(R.id.price_of_book)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.books_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context).load(data.books[position].image).into(holder.book_img)
        holder.title_of_book.text = data.books[position].title
        holder.subtitle_of_book.text = data.books[position].subtitle
        holder.price_of_book.text = data.books[position].price
        holder.itemView.setOnClickListener {
            host.findNavController()
                .navigate(SearchFragmentDirections.actionSearchFragmentToDetailsFragment(data.books[position].isbn13))
        }
    }

    override fun getItemCount(): Int {
        return data.books.size
    }

}