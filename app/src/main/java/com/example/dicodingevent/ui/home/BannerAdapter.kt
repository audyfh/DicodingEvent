package com.example.dicodingevent.ui.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.dicodingevent.data.network.response.Events
import com.example.dicodingevent.databinding.BannerCardBinding

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>(){
    private val events = mutableListOf<Events>()

    private var onItemClickCallback: ((Events) -> Unit)? = null

    fun setOnItemClickCallback(callback: (Events) -> Unit) {
        onItemClickCallback = callback
    }

    class BannerViewHolder(private val binding: BannerCardBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(event: Events){
            with(binding){
                tvEventTitle.text = event.name
                Glide.with(itemView.context)
                    .load(event.mediaCover)
                    .centerCrop()
                    .into(imgEvent)
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newEvents: List<Events>) {
        events.clear()
        events.addAll(newEvents)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = BannerCardBinding.inflate(
            LayoutInflater.from(parent.context),parent,false
        )
        return BannerViewHolder(binding)
    }

    override fun getItemCount(): Int {
       return events.size
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        val event = events[position]
        holder.bind(event)
        holder.itemView.setOnClickListener{
            onItemClickCallback?.invoke(event)
        }
    }


}