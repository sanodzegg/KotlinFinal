package com.example.booksapp.domain.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.booksapp.R
import com.example.booksapp.data.local.data.Book
import com.example.booksapp.data.network.module.BooksRecList
import com.example.booksapp.domain.entities.BooksData
import com.example.booksapp.presentation.saved.SavedFragment
import com.example.booksapp.presentation.saved.SavedFragmentDirections
import com.google.android.material.textview.MaterialTextView

class BooksListAdapter(val data :ArrayList<Book>,val host:SavedFragment) : RecyclerView.Adapter<BooksListAdapter.ViewHolder>() {

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
        Glide.with(holder.itemView.context).load(data[position].image).into(holder.book_img)
        holder.title_of_book.text = data[position].title
        holder.subtitle_of_book.text = data[position].subtitle
        holder.price_of_book.text = data[position].price
        holder.itemView.setOnClickListener {
            host.findNavController().navigate(SavedFragmentDirections.actionSavedFragmentToDetailsFragment2(data[position].isbn13))
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

}