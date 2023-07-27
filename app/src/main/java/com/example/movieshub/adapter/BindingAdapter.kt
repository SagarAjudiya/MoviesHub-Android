package com.example.movieshub.adapter

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieshub.R

@BindingAdapter(value = ["app:imageUrl"])
fun imageUrl(imageView: ImageView, url: String) {
    Glide.with(imageView.context).load(url).placeholder(R.drawable.img_movies_placeholder)
        .into(imageView)
}

@BindingAdapter("textFromInt")
fun TextView.textFromInt(votes: Int) {
    text =
        String.format("%d Votes", votes)
}

// Convert Rating to out of 5 Format and Float
@BindingAdapter("rattingFormat")
fun RatingBar.formatRatting(ratting: Double) {
    this.rating = (ratting / 2).toFloat()
}