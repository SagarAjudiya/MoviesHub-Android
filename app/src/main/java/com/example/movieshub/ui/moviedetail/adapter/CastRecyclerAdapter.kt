package com.example.movieshub.ui.moviedetail.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.movieshub.data.model.response.Cast
import com.example.movieshub.databinding.RecyclerCastBinding

class CastRecyclerAdapter : RecyclerView.Adapter<CastRecyclerAdapter.CastHolder>() {

    private var listOfCast = ArrayList<Cast>()

    class CastHolder(val binding: RecyclerCastBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            binding.cast = cast
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastHolder {
        return CastHolder(
            RecyclerCastBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun getItemCount(): Int {
        return listOfCast.size
    }

    override fun onBindViewHolder(holder: CastHolder, position: Int) {
        holder.bind(listOfCast[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(listOfCast: List<Cast>) {
        this.listOfCast.addAll(listOfCast)
        notifyDataSetChanged()
    }
}